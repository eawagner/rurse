NOTE - These scripts are a subset of the scripts from the following repo from https://github.com/eawagner/cert-scripts

cert-scriptsi
============

Scripts for quick and easy generation of ssl certificates for a development environment

WARNING!!!!
===========
These scripts should only be used in a development environment.  These scripts rely on an unguarded CA by default as the private key is fully visible.  They also intentionally cleanup any history which makes it impossible to revoke certificates or track history.

Creating server certificates
===========

To generate a JKS (Default pass is "changeit")

./gen-server-cert -J

To generate PEM private and public keys

./gen-server-cert -P

By default the scripts will use 'localhost' as the server CN.  This can be changed using the -s option.  For example:

./gen-server-cert -s devcert.com -J

Use the -h option to get a full usage description

