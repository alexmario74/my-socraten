package netstats

import (
	"bufio"
	"encoding/csv"
	"fmt"
	"io"
	"os"
	"strings"
)

func AnalyzeFile(csvFileName string) (uint32, uint32, error) {
	csvFile, err := os.Open(csvFileName)
	if err != nil {
		return 0, 0, err
	}

	csvReader := csv.NewReader(bufio.NewReader(csvFile))
	csvReader.Comma = ';'

	var (
		countRadio uint32 = 0
		countCore  uint32 = 0
	)

	for {
		record, err := csvReader.Read()
		if err == io.EOF {
			break
		}

		if err != nil {
			return 0, 0, err
		}

		service, err := classToService(calculateMoClass(record[0], record[3]))

		if err != nil {
			continue
		}

		if service.Kind == "Radio" {
			countRadio++
		} else {
			countCore++
		}
	}

	return countRadio, countCore, nil
}

func calculateMoClass(mo, sn string) string {
	moByComponents := strings.Split(mo, "/")
	moSnWithType := moByComponents[len(moByComponents)-1]

	return strings.TrimSuffix(moSnWithType, "-"+sn)
}

type MoKind struct {
	Kind, Model, Technology string
}

func classToService(moClass string) (MoKind, error) {
	switch moClass {
	case "ETAC", "NTAC", "HTAC":
		return MoKind{"Radio", "Area", "4G"}, nil
	case "EEND", "NENB", "HENB":
		return MoKind{"Radio", "Site", "4G"}, nil
	case "ECLL", "NECL", "HECL":
		return MoKind{"Radio", "Cell", "4G"}, nil
	case "RNC":
		return MoKind{"Radio", "Area", "3G"}, nil
	case "HNDB", "ENDB", "EBTS":
		return MoKind{"Radio", "Site", "3G"}, nil
	case "WCEL", "ERCL", "HRCL":
		return MoKind{"Radio", "Cell", "3G"}, nil
	case "BCF", "BSC", "EBSC", "HBSC":
		return MoKind{"Radio", "Area", "2G"}, nil
	case "BTS", "HBTS", "WBTS":
		return MoKind{"Radio", "Site", "2G"}, nil
	case "ESCE", "HWCL":
		return MoKind{"Radio", "Cell", "2G"}, nil
	case "EMGW":
		return MoKind{"Core", "", "MGW"}, nil
	case "EGGS":
		return MoKind{"Core", "", "GGSN"}, nil
	case "EHLR":
		return MoKind{"Core", "", "HLR"}, nil
	case "EINS":
		return MoKind{"Core", "", "INS"}, nil
	case "EMCS":
		return MoKind{"Core", "", "MSC"}, nil
	case "ESGS":
		return MoKind{"Core", "", "MME"}, nil
	case "ESMS":
		return MoKind{"Core", "", "SMSC"}, nil
	case "ETRP":
		return MoKind{"Core", "", "TR STP IP"}, nil
	case "ETSC":
		return MoKind{"Core", "", "TR TSC"}, nil
	case "EHSS":
		return MoKind{"Core", "", "HSS"}, nil
	case "ESBG":
		return MoKind{"Core", "", "SBG"}, nil
	default:
		return MoKind{}, fmt.Errorf("no service for class %s", moClass)
	}
}
