/*************************************************
Author:MR.Z
Date:2015-3-19
Version:1.0
Description:实现接收服务器发送的信息和资源展示
**************************************************/

#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QListWidgetItem>
#include <QList>
#include "loginwindow.h"

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    ~MainWindow();
    void getUrlFromServer(QString str); //设置资源字符串



private slots:
   void generateUrlList(QString);  //把服务器的资源字符串变成资源列表
   void getItem(QListWidgetItem*); //获取资源列表的Item项Index
   void getUrlFromItem(QString);   //从所选的Item项中获取Url地址
   void setWebViewUrl(QString str); //设置Webview的Url

private:
    Ui::MainWindow *ui;
    QListWidgetItem *m_item;
    QString m_url;


signals:
    void generated(QString);
    void choosed(QString);
    void sendItemUrl(QString);
};

#endif // MAINWINDOW_H
