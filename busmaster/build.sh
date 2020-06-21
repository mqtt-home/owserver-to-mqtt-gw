#!/bin/bash

SCRIPTDIR="$(cd "$(dirname "$0")" && pwd)"

echo "Building pharndt/1wire"
cd $SCRIPTDIR
docker build -t pharndt/1wire .