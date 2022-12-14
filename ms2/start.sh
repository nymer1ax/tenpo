#!/bin/bash
gradle --version
gradle build --continuous -PskipDownload=true --console=plain &
gradle bootRun -PskipDownload=true --console=plain