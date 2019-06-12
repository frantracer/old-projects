#!/usr/bin/perl
use strict;
use warnings;

my $arg = shift @ARGV;

if('console' eq $arg) {
    exec('/bin/bash');
} elsif('production' eq $arg) {
    chdir("/app");
    system("sbt", " assembly");
    exec("java", "-jar", "/app/target/scala-2.12/coretents-processor-assembly-1.0.jar");
} elsif('development' eq $arg) {
    chdir("/share/app");
    system("sbt", "run");
} else {
    exec($arg, @ARGV);
}
