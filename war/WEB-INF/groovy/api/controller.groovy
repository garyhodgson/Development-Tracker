package api

def LATEST_VERSION = "1"

def version = params.version?:LATEST_VERSION

forward "/api/v${version}/developments.groovy"