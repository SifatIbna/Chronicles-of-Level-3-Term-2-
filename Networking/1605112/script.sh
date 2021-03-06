echo "throughput,delay,delivery_ratio,drop_ratio" >> outputgrid.csv

ns wireless.tcl 250 40 20
awk -f parse.awk trace.tr >> outputgrid.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 40 20
awk -f parse.awk trace.tr >> outputgrid.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 750 40 20
awk -f parse.awk trace.tr >> outputgrid.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 1000 40 20
awk -f parse.awk trace.tr >> outputgrid.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 1250 40 20
awk -f parse.awk trace.tr >> outputgrid.csv
rm -rf trace.tr
rm -rf animation.nam


echo "throughput,delay,delivery_ratio,drop_ratio" >> outputnode.csv

ns wireless.tcl 500 20 20
awk -f parse.awk trace.tr >> outputnode.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 40 20
awk -f parse.awk trace.tr >> outputnode.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 60 20
awk -f parse.awk trace.tr >> outputnode.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 80 20
awk -f parse.awk trace.tr >> outputnode.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 100 20
awk -f parse.awk trace.tr >> outputnode.csv
rm -rf trace.tr
rm -rf animation.nam

echo "throughput,delay,delivery_ratio,drop_ratio" >> outputflow.csv

ns wireless.tcl 500 40 10
awk -f parse.awk trace.tr >> outputflow.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 40 20
awk -f parse.awk trace.tr >> outputflow.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 40 30
awk -f parse.awk trace.tr >> outputflow.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 40 40
awk -f parse.awk trace.tr >> outputflow.csv
rm -rf trace.tr
rm -rf animation.nam

ns wireless.tcl 500 40 50
awk -f parse.awk trace.tr >> outputflow.csv
rm -rf trace.tr
rm -rf animation.nam

python plot.py
rm -rf outputflow.csv
rm -rf outputgrid.csv
rm -rf outputnode.csv