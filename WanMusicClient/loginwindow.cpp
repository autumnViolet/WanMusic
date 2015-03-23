/*************************************************
Author:MR.Z
Date:2015-3-19
Version:1.0
Description:从服务器获取秘钥生成验证码，用于客户端登陆
**************************************************/


#include "loginwindow.h"
#include "ui_loginwindow.h"
#include "urlwindow.h"

login::login(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::loginwindow)
{
    ui->setupUi(this);

    m_client = new QTcpSocket(this);  //建立一个新的端口
    m_client->connectToHost(QHostAddress("192.168.23.1"),6665);
    //从服务器获取字符流
    connect(m_client, SIGNAL(readyRead()), this, SLOT(getMessageFromServer()));

    const char *sendToServerData = "pc\n";  //发送给服务器的验证信息
    m_client->write(sendToServerData); //向服务器发送信息

    //将字符流转换成图片
    connect(this, SIGNAL(sendImageByte(QByteArray)), this, SLOT(transformByteToQRImage(QByteArray)));

    //获取服务器传来的Url字符串
    m_server = new QTcpServer(this);
    m_server->listen(QHostAddress::Any, 6665);
    connect(m_server, SIGNAL(newConnection()), this, SLOT(acceptUrlFromServer()));
}

login::~login()
{
    delete ui;
}


/**
 * @brief login::getMessageFromServer
 * @input no
 * @output 发送相关信号（图片）
 */
void login::getMessageFromServer()
{

    m_rByteFromServer = m_client->readAll(); //读取服务器传来的字符流
    emit sendImageByte(m_rByteFromServer);  //发送生成图片的信号

}


/**
 * @brief login::transformByteToImage
 * @input 来自服务器的字符流
 * @output 显示二维码图片
 */
void login::transformByteToQRImage(QByteArray fromServer)
{
    QImage image = QImage::fromData(m_rByteFromServer); //将字符流转化为图片
    ui->imagelable->setPixmap(QPixmap::fromImage(image).scaled(200, 200)); //显示图片
}

/**
 * @brief login::transformByteToUrlWindow
 * @input 来自服务器的字符流
 * @output 生成资源窗口
 */
void login::transformByteToUrlWindow()
{
    QString str = m_client->readAll();
    MainWindow *newUrlWindow = new MainWindow(this);
    newUrlWindow->show();
    newUrlWindow->getUrlFromServer(str);
    this->destroy();
}

/**
 * @brief login::transformByteToUrlWindow
 * @input 来自服务器的链接请求
 * @output 调用生成UrlWindow的槽函数
 */
void login::acceptUrlFromServer()
{
    m_client = m_server->nextPendingConnection();
    connect(m_client, SIGNAL(readyRead()), this, SLOT(transformByteToUrlWindow()));
}
