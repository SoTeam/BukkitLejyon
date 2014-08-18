package so.team.bukkitlejyon.cmdBlock.api;

public abstract interface InputGuiBase<T>
{
  public abstract T getDefaultText();
  
  public abstract void onConfirm(InputPlayer paramInputPlayer, T paramT);
  
  public abstract void onCancel(InputPlayer paramInputPlayer);
}
