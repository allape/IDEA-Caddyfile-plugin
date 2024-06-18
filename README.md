# Caddyfile language support

## Plugin Marketplace

https://plugins.jetbrains.com/plugin/24642-caddyfile

## Known Issues

- Lack of testing, only tested with simple Caddyfiles
- Unable to find usage of matcher declaration, but it works at matcher reference
- ICON file may violate legal issue
    - ICON file is copied from https://github.com/caddyserver/caddy/blob/master/README.md, and modified to remove text

## Dev

- Install [Grammar-Kit](https://plugins.jetbrains.com/plugin/6606-grammar-kit) plugin
- Install [Plugin DevKit](https://plugins.jetbrains.com/plugin/22851-plugin-devkit) plugin
- Open [Caddyfile.simple.bnf](src/main/kotlin/cc/allape/caddyfile/Caddyfile.simple.bnf),
  right-click on the editor and select "Generate Parser Code"
- Open [Caddyfile.simple.flex](src/main/kotlin/cc/allape/caddyfile/Caddyfile.simple.flex),
  right-click on the editor and select "Run JFlex Generator"
    - At first time generation, you will be prompted to install `JFlex plugin`
- Run Gradle task `intellij/runIde` to run
    - Or run `intellij/buildPlugin` to build this plugin, and installable zip file will be located
      at [build/distributions](build/distributions)
