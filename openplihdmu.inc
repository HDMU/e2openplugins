DEPENDS += "enigma2"

SRC_URI = "git://github.com/HDMU/e2openplugin-${MODULE}.git;protocol=git"

S="${WORKDIR}/git"

SRCREV_pn-${PN} ?= "${AUTOREV}"
