/*************************************************
Author:MR.Z
Date:2015-3-19
Version:1.0
Description:从服务器获取秘钥生成验证码，用于客户端登陆
**************************************************/

#ifndef LOGIN_H
#define LOGIN_H

#include <QWidget>
#include <QtNetwork>
#include <cstdlib>
#include "urlwindow.h"

namespace Ui {
class loginwindow;
}

class login : public QWidget
{
    Q_OBJECT

public:
    explicit login(QWidget *parent = 0);
    ~login();

private:
    Ui::loginwindow  *ui;
    QTcpSocket *m_client;  //用与服务器建立连接
    QTcpServer *m_server;  //用于获取服务器返回的数据
    QByteArray  m_rByteFromServer;  //读取服务器的字符流


private slots:
    void getMessageFromServer();   //获得服务器的应答执行的方法
    void transformByteToQRImage(QByteArray); //把字符流转换为图片
    void transformByteToUrlWindow(); //把字符流的转换为资源窗口
    void acceptUrlFromServer();  //再次从服务器获得URL地址

signals:
    void sendImageByte(QByteArray);
    void sendUrlByte(QByteArray);

};

#endif // LOGIN_H
