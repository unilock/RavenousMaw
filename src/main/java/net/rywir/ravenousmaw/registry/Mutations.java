package net.rywir.ravenousmaw.registry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.rywir.ravenousmaw.RavenousMaw;
import net.rywir.ravenousmaw.system.ability.*;
import net.rywir.ravenousmaw.system.interfaces.IMutationAbility;
import net.rywir.ravenousmaw.util.dstruct.CircularList;

import java.util.*;

public enum Mutations {
    TECTONIC_BITE(Component.translatable("mutation.ravenousmaw.tectonic_bite"), Stages.LATENT, Items.STICKY_PISTON, Set.of(), new TectonicBite(), List.of(Parameters.TECTONIC_AREA)),
    COMBUSTIVE_ENZYME(Component.translatable("mutation.ravenousmaw.combustive_enzyme"), Stages.ADVANCED, Items.MAGMA_CREAM, Set.of(), new CombustiveEnzyme(), List.of()),
    INSATIABLE_VORACITY(Component.translatable("mutation.ravenousmaw.insatiable_voracity"), Stages.ADVANCED, Items.ENCHANTED_GOLDEN_APPLE, Set.of(), new InsatiableVoracity(), List.of()),
    IRIS_OUT(Component.translatable("mutation.ravenousmaw.iris_out"), Stages.ADVANCED, Items.BREEZE_ROD, Set.of(), new IrisOut(), List.of(Parameters.LIVING_PROJECTILE, Parameters.GRIM_TRACER)),
    UNDYING_FLESH(Component.translatable("mutation.ravenousmaw.undying_flesh"), Stages.NOBLE, Items.TOTEM_OF_UNDYING, Set.of(AbilityTypes.ON_CRAFT), new UndyingFlesh(), List.of()),
    SYMBIOTIC_AID(Component.translatable("mutation.ravenousmaw.symbiotic_aid"), Stages.NOBLE, Items.PUFFERFISH, Set.of(), new SymbioticAid(), List.of(Parameters.SYMBIOTIC_IMMUNITY)),
    TENDRIL_LASH(Component.translatable("mutation.ravenousmaw.tendril_lash"), Stages.NOBLE, Items.ANGLER_POTTERY_SHERD, Set.of(AbilityTypes.ON_CRAFT), new TendrilLash(), List.of()),
    RESONANT_RENDING(Component.translatable("mutation.ravenousmaw.resonant_rending"), Stages.EXCELSIOR, Items.GHAST_TEAR, Set.of(), new ResonantRending(), List.of()),
    ARCANE_HYPERTROPHY(Component.translatable("mutation.ravenousmaw.arcane_hypertrophy"), Stages.EXCELSIOR, Items.TORCHFLOWER_SEEDS, Set.of(), new ArcaneHypertrophy(), List.of()),
    INDOMITABLE_WILL(Component.translatable("mutation.ravenousmaw.indomitable_will"), Stages.EXCELSIOR, Items.DRAGON_BREATH, Set.of(), new IndomitableWill(), List.of()),
    ADAPTIVE_SHIFT(Component.translatable("mutation.ravenousmaw.adaptive_shift"), Stages.EXCELSIOR, Items.NETHER_STAR, Set.of(AbilityTypes.ON_CRAFT), new AdaptiveShift(), List.of(Parameters.SILKY_FANG, Parameters.RECKLESS_DEVOURER, Parameters.EXCAVATION_HASTE));

    private final Component title;
    private final String key;
    private final Stages stage;
    private final Item mutagen;
    private final Set<AbilityTypes> types;
    private final IMutationAbility ability;
    private final ImmutableList<Parameters> parameters;

    private static final Map<String, Mutations> BY_KEY = new HashMap<>();
    private static final Map<Item, Mutations> BY_MUTAGEN = new HashMap<>();
    private static final Set<Mutations> ON_CRAFT_TYPE_MEMBERS = new HashSet<>();
    private static final Map<Parameters, Mutations> BY_PARAMETER = new EnumMap<>(Parameters.class);

    Mutations(Component title, Stages stage, Item mutagen, Set<AbilityTypes> types, IMutationAbility ability, List<Parameters> parameters) {
        this.title = title;
        this.key = this.name().toLowerCase(Locale.ROOT);
        this.stage = stage;
        this.mutagen = mutagen;
        this.types = types;
        this.ability = ability;
        this.parameters = ImmutableList.copyOf(parameters);
    }

    public String title() {
        return title.getString();
    }

    public String key() {
        return key;
    }

    public Stages stage() {
        return stage;
    }

    public Item mutagen() {
        return mutagen;
    }

    public IMutationAbility ability() {
        return ability;
    }

    public ImmutableList<Parameters> parameters() {
        return parameters;
    }

    public static Set<String> titles() {
        Set<String> pseudo = new HashSet<>();

        for (var val : values()) {
            pseudo.add(val.title());
        }

        return pseudo;
    }

    public static Set<Mutations> getOnCraftTypeMembers() {
        return ON_CRAFT_TYPE_MEMBERS;
    }

    public static Mutations byMutagen(Item mutagen) {
        return BY_MUTAGEN.get(mutagen);
    }

    public static Mutations byKey(String key) {
        return BY_KEY.get(key);
    }

    public static Mutations byParameter(Parameters parameter) {
        return BY_PARAMETER.get(parameter);
    }

    static {
        for (Mutations mutation : values()) {
            if (mutation.types.contains(AbilityTypes.ON_CRAFT)) {
                ON_CRAFT_TYPE_MEMBERS.add(mutation);
            }
        }
    }

    static {
        for (Mutations mutation : values()) {
            BY_KEY.put(mutation.key(), mutation);
        }
    }

    static {
        for (Mutations mutation : values()) {
            BY_MUTAGEN.put(mutation.mutagen, mutation);
        }
    }

    static {
        for (Mutations mutation : values()) {
            for (Parameters parameter : mutation.parameters) {
                if (BY_PARAMETER.put(parameter, mutation) != null) {
                    throw new IllegalStateException(
                        "Parameter " + parameter.name() + " is assigned to more than one Mutation!"
                    );
                }
            }
        }
    }

    public enum AbilityTypes {
        ON_CRAFT
    }

    public enum Parameters {
        TECTONIC_AREA(Component.translatable("parameter.ravenousmaw.tectonic_area"), Type.VALUE, Component.translatable("description.ravenousmaw.tectonic_area"), CircularList.of(1, 3, 5, 7, 9)),
        SYMBIOTIC_IMMUNITY(Component.translatable("parameter.ravenousmaw.symbiotic_immunity"), Type.TOGGLE, Component.translatable("description.ravenousmaw.symbiotic_immunity"), CircularList.of(0, 1)),
        SILKY_FANG(Component.translatable("parameter.ravenousmaw.silky_fang"), Type.TOGGLE, Component.translatable("description.ravenousmaw.silky_fang"), CircularList.of(0, 1)),
        EXCAVATION_HASTE(Component.translatable("parameter.ravenousmaw.excavation_haste"), Type.VALUE, Component.translatable("description.ravenousmaw.excavation_haste"), CircularList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)),
        DEXTEROUS_TOUCH(Component.translatable("parameter.ravenousmaw.dexterous_touch"), Type.TOGGLE, Component.translatable("description.ravenousmaw.dexterous_touch"), CircularList.of(0, 1)),
        LIVING_PROJECTILE(Component.translatable("parameter.ravenousmaw.living_projectile"), Type.TOGGLE, Component.translatable("description.ravenousmaw.living_projectile"), CircularList.of(0, 1)),
        GRIM_TRACER(Component.translatable("parameter.ravenousmaw.grim_tracer"), Type.TOGGLE, Component.translatable("description.ravenousmaw.grim_tracer"), CircularList.of(0, 1)),
        RECKLESS_DEVOURER(Component.translatable("parameter.ravenousmaw.reckless_devourer"), Type.TOGGLE, Component.translatable("description.ravenousmaw.reckless_devourer"), CircularList.of(0, 1));

        private final String key;
        private final Component title;
        private final Component description;
        private final ResourceLocation icon;
        private final CircularList values;
        private final Type type;

        private static final Map<String, Parameters> BY_KEY = new HashMap<>();

        Parameters(Component title, Type type, Component description, CircularList values) {
            this.title = title;
            this.type = type;
            this.key = this.name().toLowerCase(Locale.ROOT);
            this.description = description;
            this.icon = ResourceLocation.fromNamespaceAndPath(RavenousMaw.MOD_ID, "textures/icon/" + this.key + ".png");
            this.values = values;
        }

        public Component title() {
            return title;
        }

        public String key() {
            return key;
        }

        public Component description() {
            return description;
        }

        public CircularList revolver() {
            return values;
        }

        public ResourceLocation icon() {
            return icon;
        }

        public CircularList list() {
            return this.values;
        }

        public Type type() {
            return type;
        }

        public int next(int i) {
            return values.byValue(i).next().val();
        }

        public int prev(int i) {
            return values.byValue(i).prev().val();
        }

        public int head() {
            return list().head().val();
        }

        public static Parameters byKey(String key) {
            return BY_KEY.get(key);
        }

        static {
            for (Parameters parameter : values()) {
                BY_KEY.put(parameter.key(), parameter);
            }
        }

        public enum Type {
            TOGGLE,
            VALUE
        }
    }
}
