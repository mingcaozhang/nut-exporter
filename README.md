# nut-monitor

A simple Prometheus/Grafana/AlertManager stack to monitor your UPS power draw and battery life. 

## Prerequisites

You will need to have `nut-server` running on a host with a connection to the UPS (typically via USB) and it needs to
be accessible from wherever you wish to run this application. 

To run this locally, you will need to have `nut` installed. You can follow these 
[instructions](https://wiki.archlinux.org/index.php/Network_UPS_Tools).