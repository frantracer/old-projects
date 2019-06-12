#!/usr/bin/perl
use strict;
use warnings;

my $arg = shift @ARGV;

my $sites_available_path = "/etc/nginx/sites-available";
my $sites_enabled_path = "/etc/nginx/sites-enabled";

if(-e "$sites_available_path/$arg") {
    # Configure nginx servers
    system("unlink", "$sites_enabled_path/default");
    my @enabled_configs = ($arg, "https-redirection");
    for my $config (@enabled_configs) {
        system("ln", "-sfn", "$sites_available_path/$config", "$sites_enabled_path/$config");
    }

    # Launch nginx
    exec("nginx", "-g", "daemon off;", @ARGV);
} else {
    exec($arg, @ARGV);
}
