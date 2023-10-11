# Workstation: Backup and Restore

Very good initial documentation: <https://www.jbang.dev/documentation/guide/latest/index.html>

```bash
asdf plugin add jbang
asdf list all jbang
asdf install jbang 0.111.0
asdf global jbang 0.111.0
asdf local jbang 0.111.0
jbang init -t cli BackupRestore.java

# run
jbang BackupRestore.java
jbang BackupRestore.java --help
jbang BackupRestore.java mhagnumdw
./BackupRestore.java
./BackupRestore.java --help
./BackupRestore.java mhagnumdw
```

```bash
asdf install java graalvm-community-17.0.8
asdf local java graalvm-community-17.0.8
jbang export native --force --verbose BackupRestore.java
upx -7 -k BackupRestore # decrease executable size
```

```bash
# to install wrapper, like maven wrapper, so you don't
# need to have JBang installed
jbang wrapper install

./jbang BackupRestore.java mhagnumdw
```

Editar o projeto no vscode e o vscode reconhecer as mudanças automaticamente

> ref: <https://www.jbang.dev/documentation/guide/latest/editing.html#live-editing>

- Instalar a extensão `JBang` no vscode
- `jbang edit --live --open=code BackupRestore.java`
