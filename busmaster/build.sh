#!/bin/bash

SCRIPTDIR="$(cd "$(dirname "$0")" && pwd)"

echo "Building pharndt/ds9490r-owfs"
cd $SCRIPTDIR
docker build -t pharndt/ds9490r-owfs .