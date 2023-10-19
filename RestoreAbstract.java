/**
 * Implementação comum das rotinas de restauração. Normalmente rotinas de restauração
 * devem herdar dessa classe ao invés de implementar a interface Restore.
 */
public abstract class RestoreAbstract implements Restore {

    protected final Logger log = Logger.getLogger(getClass());

}
