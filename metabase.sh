#!/bin/bash

#################################################################
# 作者：Dylan <1214966109@qq.com>
# 时间：2018-03-29
# 用途：Metabase 启动管理
#################################################################
if [ -f /etc/init.d/functions ]; then
    . /etc/init.d/functions
fi


#################################################################
# 定义变量
#################################################################
export MB_DB_TYPE=mysql
export MB_DB_DBNAME=metabase
export MB_DB_PORT=3306
export MB_DB_USER=root
export MB_DB_PASS=Password01
export MB_DB_HOST=localhost
export MB_DB_CONNECTION_URI="mysql://localhost:3306/metabase?user=root&password=Password01&useSSL=false"
SERVICE_NAME='metabase'
SERVICE_PACKAGE="${SERVICE_NAME}.jar"
SERVICE_PATH='/share/metabase'
LOG_PATH="${SERVICE_PATH}/logs"
JAVA_CMD='java'


#################################################################
# 判断日志目录
#################################################################
if [[ ! -d ${LOG_PATH} ]]; then
    mkdir -p ${LOG_PATH}
fi


#################################################################
# 定义命令
#################################################################
function START_COMMAND()
{
    ${JAVA_CMD} -Duser.timezone=Asia/Shanghai -Xms4g -Xmx4g -jar ${SERVICE_PATH}/${SERVICE_PACKAGE} >> ${LOG_PATH}/${SERVICE_NAME}.log &
    if [[ $? -eq 0 ]]; then
        action "${SERVICE_NAME} start successed" /bin/true
    else
        action "${SERVICE_NAME} start failed" /bin/false
    fi
}

function STOP_COMMAND()
{
    SERVICE_PID=`ps -ef | grep "${SERVICE_PACKAGE}" | grep -v 'grep' | awk '{print $2}'`
    if [[ ${SERVICE_PID} == '' ]]; then
        action "${SERVICE_NAME} is not running" /bin/false
    else
        kill -9 ${SERVICE_PID} >/dev/null 2>&1
        if [[ $? -eq 0 ]]; then
            action "${SERVICE_NAME} stop successed" /bin/true
        else
            action "${SERVICE_NAME} stop failed" /bin/false
        fi
    fi
}

function STATUS_COMMAND()
{
    SERVICE_PID=`ps -ef | grep "${SERVICE_PACKAGE}" | grep -v 'grep' | awk '{print $2}'`
    if [[ ${SERVICE_PID} == '' ]]; then
        action "${SERVICE_NAME} is not running" /bin/false
    else
        action "${SERVICE_NAME} is running" /bin/true
    fi
}


#################################################################
# 定义命令
#################################################################
case "$1" in
    start)
        START_COMMAND
        ;;
    stop)
        STOP_COMMAND
        ;;
    restart|reload)
        STOP_COMMAND
        START_COMMAND
        ;;
    status)
        STATUS_COMMAND
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status|reload}"
        ;;
esac
