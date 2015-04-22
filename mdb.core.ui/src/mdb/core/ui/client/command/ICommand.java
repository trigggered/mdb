
package mdb.core.ui.client.command;

public interface ICommand<E> {
    public void execute(E sender);
}
