package com.feed_the_beast.mods.ftbguilibrary.widget;

import com.feed_the_beast.mods.ftbguilibrary.icon.Icon;
import com.feed_the_beast.mods.ftbguilibrary.utils.MouseButton;
import com.feed_the_beast.mods.ftbguilibrary.utils.TooltipList;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nullable;
import java.util.function.BooleanSupplier;

/**
 * @author LatvianModder
 */
public class ContextMenuItem implements Comparable<ContextMenuItem>
{
	public static final ContextMenuItem SEPARATOR = new ContextMenuItem(StringTextComponent.EMPTY, Icon.EMPTY, () -> {})
	{
		@Override
		public Widget createWidget(ContextMenu panel)
		{
			return new ContextMenu.CSeperator(panel);
		}
	};

	public static final BooleanSupplier TRUE = () -> true;
	public static final BooleanSupplier FALSE = () -> false;

	public ITextComponent title;
	public Icon icon;
	public Runnable callback;
	public BooleanSupplier enabled = TRUE;
	public ITextComponent yesNoText = new StringTextComponent("");
	public boolean closeMenu = true;

	public ContextMenuItem(ITextComponent t, Icon i, @Nullable Runnable c)
	{
		title = t;
		icon = i;
		callback = c;
	}

	public void addMouseOverText(TooltipList list)
	{
	}

	public void drawIcon(MatrixStack matrixStack, Theme theme, int x, int y, int w, int h)
	{
		icon.draw(matrixStack, x, y, w, h);
	}

	public ContextMenuItem setEnabled(boolean v)
	{
		return setEnabled(v ? TRUE : FALSE);
	}

	public ContextMenuItem setEnabled(BooleanSupplier v)
	{
		enabled = v;
		return this;
	}

	public ContextMenuItem setYesNo(ITextComponent s)
	{
		yesNoText = s;
		return this;
	}

	public ContextMenuItem setCloseMenu(boolean v)
	{
		closeMenu = v;
		return this;
	}

	public Widget createWidget(ContextMenu panel)
	{
		return new ContextMenu.CButton(panel, this);
	}

	@Override
	public int compareTo(ContextMenuItem o)
	{
		return title.getString().compareToIgnoreCase(o.title.getString());
	}

	public void onClicked(Panel panel, MouseButton button)
	{
		if (closeMenu)
		{
			panel.getGui().closeContextMenu();
		}

		callback.run();
	}
}