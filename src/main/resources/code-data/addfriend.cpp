#include "addfriend.h"
#include "ui_addfriend.h"
#include <QMessageBox>
#include <QDebug>
#include <logindialog.h>
addfriend::addfriend(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::addfriend)
{
    ui->setupUi(this);
    tcpSocket = new QTcpSocket();
}

addfriend::~addfriend()
{
    delete ui;
}

void addfriend::on_sure_clicked()
{

    QString name=ui->name->text();
    QString ip=ui->ip->text();
    QString port=ui->port->text();
    tcpSocket->abort();
    tcpSocket->connectToHost("127.0.0.1", 8899);
    if(!tcpSocket->waitForConnected(30000))
    {
        QMessageBox::warning(this, "Warning!", "网络错误", QMessageBox::Yes);
    }
    QString loginmessage = QString("addfriend##%1##%2##%3##%4").arg(LoginDialog::usrname,name,ip,port);
    tcpSocket->write(loginmessage.toUtf8());
    tcpSocket->flush();
    connect(tcpSocket,&QTcpSocket::readyRead,[=](){
        QByteArray buffer = tcpSocket->readAll();
        if(QString(buffer).section("##",0,0)==QString("successed"))
        {//登陆成功
            QMessageBox::information(this, "Note", "添加好友成功", QMessageBox::Yes);
        }

    });
    accept();
}