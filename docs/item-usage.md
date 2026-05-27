# Item Usage Examples

Slots in a Layout are filled with an **`ItemLayout`** — the unit that pairs a visual **`Item`**
with the slot's behavior. There are two built-in kinds:

- **`StaticItem`** — purely visual, ignores clicks.
- **`InteractiveItem`** — visual plus per click-type handlers.

The visual look is provided by an `Item` implementation such as `MaterialItem` or `SkullItem`.
The recommended entry point for building either kind is the **`ItemSlot`** facade.

---

### Display item (`ItemSlot.display`)

A display-only slot renders an item with **no click actions**.

```java
final ItemLayout border = ItemSlot.display(
        MaterialItem.builder()
                .material(Material.GRAY_STAINED_GLASS_PANE)
                .displayName(Component.text("Basic Stone", NamedTextColor.GRAY))
                .lore(
                        Component.text("This is a stone", NamedTextColor.DARK_GRAY),
                        Component.text("Static item example", NamedTextColor.GRAY)
                )
                .build()
);
```

- **MaterialItem** — defines the material, amount, name, lore and (optional) custom model data.
- **ItemSlot.display(...)** — wraps the item for display without interactions.

---

### Interactive item (`ItemSlot.interactive`)

An interactive slot responds to clicks. `ItemSlot.interactive(item)` pre-fills the visual item,
so you go straight to the click handlers.

```java
final ItemLayout sword = ItemSlot.interactive(
                MaterialItem.builder()
                        .material(Material.DIAMOND_SWORD)
                        .displayName(Component.text("Epic Sword", NamedTextColor.AQUA))
                        .lore(Component.text("An interactive sword!", NamedTextColor.GRAY))
                        .build())
        .leftClick(ctx -> ctx.player().sendMessage("Left click!"))
        .rightClick(ctx -> ctx.player().sendMessage("Right click!"))
        .middleClick(ctx -> ctx.player().sendMessage("Middle click!"))
        .build();
```

Available handlers: `leftClick`, `rightClick`, `middleClick`, `doubleClick`, `shiftLeftClick`,
`shiftRightClick`, `numberKeyClick`, `dropClick`, `controlDropClick`, `swapOffhandClick`.

You can also bind several click types at once, or a fallback:

```java
final ItemLayout button = ItemSlot.interactive(item)
        // bind specific click types to the same handler
        .clicks(ctx -> ctx.player().sendMessage("Clicked!"), ItemClickType.LEFT, ItemClickType.RIGHT)
        // fallback for every click type not bound above — call this LAST
        .otherwiseClick(ctx -> ctx.player().sendMessage("Some other click: " + ctx.clickType().name()))
        .build();
```

> `otherwiseClick(...)` only fills the click types that have no handler at the moment it is called,
> so register it after your specific handlers.

---

### Skull items

`SkullItem` is just another `Item`, so it works with both `ItemSlot.display` and `ItemSlot.interactive`:

```java
final ItemLayout head = ItemSlot.interactive(
                SkullItem.builder()
                        // Option 1: a player's profile
                        .fromPlayer(player)
                        // Option 2: an existing PlayerProfile
                        // .fromProfile(profile)
                        // Option 3: a Base64 texture value
                        // .fromBase64("eyJ0ZXh0dXJlcyI6...")
                        // Option 4: a Mojang texture id
                        // .fromTextureId("e609e36c6d6a631eb7b76b3eded9ccb37d2fea82031b50479be364bbd01e6340")
                        .build())
        .leftClick(ctx -> ctx.player().sendMessage("You clicked a skull!"))
        .build();
```

---

### Notes

- `ItemSlot.display(...)` is ideal for static decorative items or visual fillers.
- `ItemSlot.interactive(...)` gives full control over player interactions through the `ClickContext`.
- Both kinds can be freely combined within the same layout.
