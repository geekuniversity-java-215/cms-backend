#!/usr/bin/env bash

sudo -u postgres PGOPTIONS=--search_path=cms psql --dbname=cms -f infrastructure/database/purge_schema.sql
