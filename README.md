# nut-monitor

A simple Prometheus exporter for NUT (Network UPS Tools). 

## Prerequisites

You will need to have `nut-server` running on a host with a connection to the UPS (typically via USB) and it needs to
be accessible from wherever you wish to run this application. 

To run this locally, you will need to have `nut` installed. You can follow these 
[instructions](https://wiki.archlinux.org/index.php/Network_UPS_Tools).

The recommended way to run is by running 
```docker run -p 9185:9185 -e UPS_NAME=$UPSNAME -e UPSD_HOST=$UPSHOST -e UPSD_PORT=$UPSPORT mingcaozhang/nut-exporter:0.1.0
```