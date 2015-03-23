#-------------------------------------------------
#
# Project created by QtCreator 2015-03-19T18:43:58
#
#-------------------------------------------------

QT       += core gui webkitwidgets network

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = WanMusicClient
TEMPLATE = app


SOURCES += main.cpp \
    urlwindow.cpp \
    loginwindow.cpp

HEADERS  += \
    urlwindow.h \
    loginwindow.h

FORMS    += mainwindow.ui \
    loginwindow.ui
