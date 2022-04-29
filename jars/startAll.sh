#!/bin/sh

export CONFIG=nacos-demo-0.0.1.jar
export GATEWAY=gateway-server-0.0.1.jar
export CUSTOMER=account-server-0.0.1.jar
export POVIDER=system-server-0.0.1.jar
export OAUTH=oauth2-server-0.0.1.jar

export CONFIG_log=../logs/configserver.log
export GATEWAY_log=../logs/gateway.log
export CUSTOMER_log=../logs/customer.log
export POVIDER_log=../logs/provider.log

export CONFIG_port=8840
export GATEWAY_port=8088
export CUSTOMER_port=8842
export POVIDER_port=8841

case "$1" in

start)
        ## 启动config
        echo "--------开始启动CONFIG---------------"
        nohup java -jar $CONFIG > $CONFIG_log 2>&1 &
        CONFIG_pid=`lsof -i:$CONFIG_port|grep "LISTEN"|awk '{print $2}'` 
        until [ -n "$CONFIG_pid" ]
            do
              CONFIG_pid=`lsof -i:$CONFIG_port|grep "LISTEN"|awk '{print $2}'`  
            done
        echo "CONFIG pid is $CONFIG_pid"
        echo "---------CONFIG 启动成功-----------"
 
        ## 启动gateway
        echo "--------开始启动GATEWAY---------------"
        nohup java -jar $GATEWAY > $GATEWAY_log 2>&1 &
        GATEWAY_pid=`lsof -i:$GATEWAY_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$GATEWAY_pid" ]
            do
              GATEWAY_pid=`lsof -i:$GATEWAY_port|grep "LISTEN"|awk '{print $2}'`  
            done
        echo "GATEWAY pid is $GATEWAY_pid"    
        echo "---------GATEWAY 启动成功-----------"
 
        ## 启动customer
        echo "--------开始启动CUSTOMER---------------"
        nohup java -jar $CUSTOMER > $CUSTOMER_log 2>&1 &
        CUSTOMER_pid=`lsof -i:$CUSTOMER_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$CUSTOMER_pid" ]
            do
              CUSTOMER_pid=`lsof -i:$CUSTOMER_port|grep "LISTEN"|awk '{print $2}'`  
            done
        echo "CUSTOMER pid is $CUSTOMER_pid"     
        echo "---------CUSTOMER 启动成功-----------"                       
        
        ## 启动provider
        echo "--------开始启动POVIDER---------------"
        nohup java -jar $POVIDER > $POVIDER_log 2>&1 &
        POVIDER_pid=`lsof -i:$POVIDER_port|grep "LISTEN"|awk '{print $2}'`
        until [ -n "$POVIDER_pid" ]
            do
              POVIDER_pid=`lsof -i:$POVIDER_port|grep "LISTEN"|awk '{print $2}'`  
            done
        echo "POVIDER pid is $POVIDER_pid"     
        echo "---------POVIDER 启动成功-----------"
       
        echo "===startAll success==="
        ;;

 stop)
        P_ID=`ps -ef | grep -w $CONFIG | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===CONFIG process not exists or stop success"
        else
            kill -9 $P_ID
            echo "CONFIG killed success"
        fi
        
         P_ID=`ps -ef | grep -w $GATEWAY | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===GATEWAY process not exists or stop success"
        else
            kill -9 $P_ID
            echo "GATEWAY killed success"
        fi
        
         P_ID=`ps -ef | grep -w $CUSTOMER | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===CUSTOMER process not exists or stop success"
        else
            kill -9 $P_ID
            echo "CUSTOMER killed success"
        fi
                
         P_ID=`ps -ef | grep -w $POVIDER | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===POVIDER process not exists or stop success"
        else
            kill -9 $P_ID
            echo "POVIDER killed success"
        fi
       
        echo "===stop success==="
        ;;   
 
restart)
        $0 stop
        sleep 10
        $0 start
        echo "===restart success==="
        ;;   
esac    
exit 0