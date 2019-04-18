extern crate csv;

use std::error::Error;
use std::fs::File;

type Report = (u32, u32);

type ModelKind = &'static str;
type Technology = &'static str;

pub enum ServiceKind {
    Radio(ModelKind, Technology),
    Core(ModelKind),
}

pub fn calculate_mo_class<'a>(mo: &'a str, sn: &str) -> &'a str {
    let mo_class = mo.split("/").last().unwrap();

    mo_class.trim_end_matches(("-".to_owned() + sn).as_str())
}

pub fn class_to_service(mo_class: &str) -> Option<ServiceKind> {
    match mo_class {
        "ETAC" | "NTAC" | "HTAC" => Some(ServiceKind::Radio("Area", "4G")),
        "EEND" | "NENB" | "HENB" => Some(ServiceKind::Radio("Site", "4G")),
        "ECLL" | "NECL" | "HECL" => Some(ServiceKind::Radio("Cell", "4G")),
        "RNC" => Some(ServiceKind::Radio("Area", "3G")),
        "HNDB" | "ENDB" | "EBTS" => Some(ServiceKind::Radio("Site", "3G")),
        "WCEL" | "ERCL" | "HRCL" => Some(ServiceKind::Radio("Cell", "3G")),
        "BCF" | "BSC" | "EBSC" | "HBSC" => Some(ServiceKind::Radio("Area", "2G")),
        "BTS" | "HBTS" | "WBTS" => Some(ServiceKind::Radio("Site", "2G")),
        "ESCE" | "HWCL" => Some(ServiceKind::Radio("Cell", "2G")),
        "EMGW" => Some(ServiceKind::Core("MGW")),
        "EGGS" => Some(ServiceKind::Core("GGSN")),
        "EHLR" => Some(ServiceKind::Core("HLR")),
        "EINS" => Some(ServiceKind::Core("INS")),
        "EMCS" => Some(ServiceKind::Core("MSC")),
        "ESGS" => Some(ServiceKind::Core("MME")),
        "ESMS" => Some(ServiceKind::Core("SMSC")),
        "ETRP" => Some(ServiceKind::Core("TR STP IP")),
        "ETSC" => Some(ServiceKind::Core("TR TSC")),
        "EHSS" => Some(ServiceKind::Core("HSS")),
        "ESBG" => Some(ServiceKind::Core("SBG")),
        _ => {
            None
        },
    }
}

pub fn analyze_file(csv_file_name: &str) -> Result<Report, Box<Error>> {
    let csv_file = File::open(csv_file_name)?;

    let mut rdr = csv::ReaderBuilder::new()
        .delimiter(b';')
        .has_headers(false)
        .from_reader(csv_file);

    let mut count_radio: u32 = 0;
    let mut count_core: u32 = 0;

    for result in rdr.records() {
        let record = result?;

        if &record[0] == "null" || &record[3] == "null" {
            continue;
        }

        let mo_class = calculate_mo_class(&record[0], &record[3]);

        if let Some(service_kind) = class_to_service(&mo_class) {
            match service_kind {
                ServiceKind::Radio(_, _) => count_radio = count_radio + 1,
                ServiceKind::Core(_) => count_core = count_core + 1,
            }
        }
    }

    Ok((count_radio, count_core))
}
