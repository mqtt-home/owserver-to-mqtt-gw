#!/bin/bash
/etc/init.d/owserver start  # [start|stop|restart]
/etc/init.d/owhttpd start   # [start|stop|restart]
/etc/init.d/owfs start      # [start|stop|restart]

# keep running
tail -f /dev/null