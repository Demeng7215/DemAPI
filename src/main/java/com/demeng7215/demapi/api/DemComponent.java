package com.demeng7215.demapi.api;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.ComponentBuilder.FormatRetention;

public final class DemComponent {

    private BaseComponent current;
    private final List<BaseComponent> parts = new ArrayList<>();

    public DemComponent(DemComponent original) {
        current = original.current.duplicate();

        for (final BaseComponent baseComponent : original.parts)
            parts.add(baseComponent.duplicate());
    }

    public DemComponent(String text) {
        current = new TextComponent(TextComponent.fromLegacyText(MessageUtils.color(text)));
    }

    public DemComponent(BaseComponent component) {
        current = component.duplicate();
    }

    public DemComponent append(BaseComponent component) {
        return append(component, ComponentBuilder.FormatRetention.ALL);
    }

    public DemComponent append(BaseComponent component, ComponentBuilder.FormatRetention retention) {
        parts.add(current);

        final BaseComponent previous = current;
        current = component.duplicate();
        current.copyFormatting(previous, retention, false);
        return this;
    }

    public DemComponent append(BaseComponent[] components) {
        return append(components, ComponentBuilder.FormatRetention.ALL);
    }

    public DemComponent append(BaseComponent[] components, ComponentBuilder.FormatRetention retention) {
        Preconditions.checkArgument(components.length != 0, "No components to append");

        final BaseComponent previous = current;
        for (final BaseComponent component : components) {
            parts.add(current);

            current = component.duplicate();
            current.copyFormatting(previous, retention, false);
        }

        return this;
    }

    public DemComponent append(String text) {
        return append(text, ComponentBuilder.FormatRetention.FORMATTING);
    }

    public DemComponent append(String text, ComponentBuilder.FormatRetention retention) {
        parts.add(current);

        final BaseComponent old = current;
        current = new TextComponent(TextComponent.fromLegacyText(MessageUtils.color(text)));
        current.copyFormatting(old, retention, false);

        return this;
    }

    public DemComponent onClickRunCmd(String text) {
        return onClick(ClickEvent.Action.RUN_COMMAND, text);
    }

    public DemComponent onClickSuggestCmd(String text) {
        return onClick(ClickEvent.Action.SUGGEST_COMMAND, text);
    }

    public DemComponent onClick(Action action, String text) {
        current.setClickEvent(new ClickEvent(action, MessageUtils.color(text)));
        return this;
    }

    public DemComponent onHover(String text) {
        return onHover(net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT, text);
    }

    public DemComponent onHover(net.md_5.bungee.api.chat.HoverEvent.Action action, String text) {
        current.setHoverEvent(new HoverEvent(action, TextComponent.fromLegacyText(MessageUtils.color(text))));

        return this;
    }

    public DemComponent retain(FormatRetention retention) {
        current.retain(retention);
        return this;
    }

    public BaseComponent[] create() {
        final BaseComponent[] result = parts.toArray(new BaseComponent[parts.size() + 1]);
        result[parts.size()] = current;

        return result;
    }

    public void send(Player... players) {
        final BaseComponent[] comp = create();

        for (final Player player : players)
            player.spigot().sendMessage(comp);
    }

    public static DemComponent builder(String text) {
        return new DemComponent(text);
    }
}
