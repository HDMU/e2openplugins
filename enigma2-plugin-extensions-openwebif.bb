MODULE = "OpenWebif"
DESCRIPTION = "Control your receiver with a browser"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://README;firstline=10;lastline=12;md5=9c14f792d0aeb54e15490a28c89087f7"

DEPENDS = "python-cheetah-native"
RDEPENDS_${PN} = "python-cheetah python-compression python-json python-unixadmin python-misc python-modules python-pyopenssl python-shell aio-grab"
inherit gitpkgv
PV = "0.1+git${SRCPV}"
PKGV = "0.1+git${GITPKGV}"
PR = "r0.73"

require openplugins-distutils-hdmu.inc

# Just a quick hack to "compile" it
do_compile() {
    cheetah-compile -R --nobackup ${S}/plugin
    python -O -m compileall ${S}
    for f in $(find ${S}/locale -name *.po ); do
        l=$(echo ${f%} | sed 's/\.po//' | sed 's/.*locale\///')
        mkdir -p ${S}/plugin/locale/${l%}/LC_MESSAGES
        msgfmt -o ${S}/plugin/locale/${l%}/LC_MESSAGES/OpenWebif.mo ./locale/$l.po
    done
}

PLUGINPATH = "/usr/lib/enigma2/python/Plugins/Extensions/${MODULE}"
do_install_append() {
    install -d ${D}${PLUGINPATH}
    cp -rp ${S}/plugin/* ${D}${PLUGINPATH}
    find ${D}${PLUGINPATH} -name '*.py' -exec rm {} \;
}

FILES_${PN} = "${PLUGINPATH}"
