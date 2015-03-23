/*************************************************
Author:MR.Z
Date:2015-3-19
Version:1.0
Description:实现接收服务器发送的信息和资源展示
**************************************************/

#include "urlwindow.h"
#include "ui_mainwindow.h"

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    setWindowTitle("WanMusic");

    //根据服务器资源字符串产生资源列表
    connect(this, SIGNAL(generated(QString)), this, SLOT(generateUrlList(QString)));

    //响应资源列表被点击
    connect(ui->listWidget, SIGNAL(itemClicked(QListWidgetItem*)), this, SLOT(getItem(QListWidgetItem*)));
    connect(this, SIGNAL(choosed(QString)), this, SLOT(getUrlFromItem(QString)));
    connect(this, SIGNAL(sendItemUrl(QString)), this, SLOT(setWebViewUrl(QString)));

    //用于加载flash插件
    QApplication::addLibraryPath("./plugins");

    //webview默认属性配置
    ui->webView->settings()->setAttribute(QWebSettings::PluginsEnabled, true);
    ui->webView->settings()->setAttribute(QWebSettings::JavascriptEnabled, true);
    ui->webView->settings()->setAttribute(QWebSettings::DeveloperExtrasEnabled, true);
    ui->webView->settings()->setAttribute(QWebSettings::JavascriptCanOpenWindows, true);
    ui->webView->setUrl(QString("http://www.kugou.com/"));

}

MainWindow::~MainWindow()
{
    delete ui;
}


/**
 * @brief MainWindow::SetUrl
 * @input 来自服务器的资源字符串
 * @output 发送生成资源列表信号
 */
void MainWindow::getUrlFromServer(QString str)
{  
    m_url = str;
    emit generated(m_url);
}

/**
 * @brief MainWindow::setWebViewUrl
 * @input 来自getUrlFromItem发送的信号
 * @output 更新WebView Url
 */
void MainWindow::setWebViewUrl(QString str)
{
    ui->webView->setUrl("http://" + str);
    qDebug() << str;
}

/**
 * @brief MainWindow::generateUrlList
 * @input 来自服务器的资源字符串
 * @output 生成资源列表
 */
void MainWindow::generateUrlList(QString str)
{
    //分割发送来的字符串
    QStringList sList = str.split("分享歌曲，");
    QStringList subSlist;
    QString getMusicDescription;

    for (int i = 1; i < sList.count(); i++)
    {
        subSlist = sList.at(i).split("#");
    }

    for (int i = 1; i < sList.count(); i++)
    {
        subSlist = sList.at(i).split("#");
        m_item = new QListWidgetItem(ui->listWidget);
        getMusicDescription = subSlist.at(4);
        getMusicDescription[1] = QChar(' ');
        getMusicDescription = getMusicDescription.trimmed();
        m_item->setData(Qt::DisplayRole, subSlist.at(1) + "                             " + getMusicDescription);
    }


}

/**
 * @brief MainWindow::getItem
 * @input 来自用户对于某一资源Item的点击
 * @output 发出包含Item中Url的信号
 */
void MainWindow::getItem(QListWidgetItem *item)
{
    QString getUrl = (QString)(item->data(Qt::DisplayRole).toString());
    qDebug() << getUrl;
    emit(choosed(getUrl));
}

/**
 * @brief MainWindow::getUrlFromItem
 * @input 来自getItem的信号
 * @output 提取Item中的字符串，发送更新WebView信号
 */
void MainWindow::getUrlFromItem(QString str)
{
    QStringList sList = str.split("http://");
    str = sList.at(1);
    emit sendItemUrl(str);
}



