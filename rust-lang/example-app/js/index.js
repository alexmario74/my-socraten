const fs = require('fs')
const path = require('path')
const parse = require('csv-parse')
const transform = require('stream-transform')

main()

function main() {
    if (process.argv.length < 3) {
        console.error("error: a valid csv file path should be provided.")
        process.exit(-1)
    }

    let countRadio = 0
    let countCore = 0

    const parser = parse({
        delimiter: ';'
    })

    const transformer = transform((record, cb) => {
        try {
            service = classToService(
                calculateMoClass(record[0], record[3])
            )

            service.kind === 'Radio'
                ? countRadio++
                : countCore++
        } catch(e) {
        }
        setTimeout(_ => cb(), 0)
    }, {
        parallel: 1000
    })

    const reader = fs.createReadStream(
        path.join(__dirname, process.argv[2])
    )

    reader.on('end', 
        _ => console.log(`(${countRadio}, ${countCore})`)
    )

    reader
        .pipe(parser)
        .pipe(transformer)
}

const MoKind = (kind, model, technology) => ({
    kind, model, technology
})

const calculateMoClass = (mo, sn) => 
    path.basename(mo).replace(`-${sn}`, '')

const classToService = moClass => {
    switch(moClass) {
        case "ETAC": 
        case "NTAC": 
        case "HTAC":
            return MoKind("Radio", "Area", "4G")
        case "EEND":
        case "NENB":
        case "HENB":
            return MoKind("Radio", "Site", "4G")
        case "ECLL":
        case "NECL":
        case "HECL":
            return MoKind("Radio", "Cell", "4G")
        case "RNC":
            return MoKind("Radio", "Area", "3G")
        case "HNDB":
        case "ENDB":
        case "EBTS":
            return MoKind("Radio", "Site", "3G")
        case "WCEL":
        case "ERCL":
        case "HRCL":
            return MoKind("Radio", "Cell", "3G")
        case "BCF":
        case "BSC":
        case "EBSC":
        case "HBSC":
            return MoKind("Radio", "Area", "2G")
        case "BTS":
        case "HBTS":
        case "WBTS":
            return MoKind("Radio", "Site", "2G")
        case "ESCE":
        case "HWCL":
            return MoKind("Radio", "Cell", "2G")
        case "EMGW":
            return MoKind("Core", "", "MGW")
        case "EGGS":
            return MoKind("Core", "", "GGSN")
        case "EHLR":
            return MoKind("Core", "", "HLR")
        case "EINS":
            return MoKind("Core", "", "INS")
        case "EMCS":
            return MoKind("Core", "", "MSC")
        case "ESGS":
            return MoKind("Core", "", "MME")
        case "ESMS":
            return MoKind("Core", "", "SMSC")
        case "ETRP":
            return MoKind("Core", "", "TR STP IP")
        case "ETSC":
            return MoKind("Core", "", "TR TSC")
        case "EHSS":
            return MoKind("Core", "", "HSS")
        case "ESBG":
            return MoKind("Core", "", "SBG")
        default:
            throw new Error('no service for class ' + moClass)
    }
}
