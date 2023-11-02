# ⚠️ Still very premature, unusable for now

# Workstation: Backup and Restore

Very good initial documentation: <https://www.jbang.dev/documentation/guide/latest/index.html>

```bash
asdf plugin add jbang
asdf list all jbang
asdf install jbang 0.111.0
asdf global jbang 0.111.0
asdf local jbang 0.111.0
jbang init -t cli DwWorkstation.java

# run
jbang DwWorkstation.java
jbang DwWorkstation.java --help
jbang DwWorkstation.java mhagnumdw
./DwWorkstation.java
./DwWorkstation.java --help
./DwWorkstation.java mhagnumdw
```

```bash
asdf install java graalvm-community-17.0.8
asdf local java graalvm-community-17.0.8
jbang export native --force --verbose DwWorkstation.java
upx -7 -k DwWorkstation # decrease executable size
```

```bash
# to install wrapper, like maven wrapper, so you don't
# need to have JBang installed
jbang wrapper install

./jbang DwWorkstation.java mhagnumdw
```

Editar o projeto no vscode e o vscode reconhecer as mudanças automaticamente

> ref: <https://www.jbang.dev/documentation/guide/latest/editing.html#live-editing>

- Instalar a extensão `JBang` no vscode
- `jbang edit --live --open=code DwWorkstation.java`

Debugar:

- jbang --debug DwWorkstation.java backup --backup-root-dir=/home/mhagnumdw/Dropbox/backup/teste-dw-backup
- Na view debug do vscode, executar `DwWorkstation`
