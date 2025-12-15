package net.rywir.ravenousmaw.system;

import com.google.common.collect.ImmutableMap;
import com.ibm.icu.impl.CollectionSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rywir.ravenousmaw.RavenousMaw;
import net.rywir.ravenousmaw.content.component.MutationComponent;
import net.rywir.ravenousmaw.registry.DataComponentTypes;
import net.rywir.ravenousmaw.registry.Mutations;

import java.util.*;

public class MutationHandler {
    private final ItemStack stack;
    private final ImmutableMap<String, MutationComponent.ConfigurationComponent> mutmap;
    private static final int DEFAULT_VALUE = 1;

    public MutationHandler(ItemStack stack) {
        this.stack = stack;
        this.mutmap = stack.get(DataComponentTypes.MUTATION_COMPONENT_TYPE).mutmap();
    }

    public void syncMutations(ItemStack target) {
        MutationComponent component = stack.get(DataComponentTypes.MUTATION_COMPONENT_TYPE);
        target.set(DataComponentTypes.MUTATION_COMPONENT_TYPE, new MutationComponent(component.mutmap()));
    }

    public boolean has(Mutations mutation) {
        return mutmap.containsKey(mutation.key());
    }

    public void add(Mutations mutation) {
        if(has(mutation)) {
            return;
        }

        Map<String, Integer> pseudoConfmap = new HashMap<>();
        mutation.parameters().forEach(parameter -> {
            pseudoConfmap.put(parameter.key(), DEFAULT_VALUE);
        });

        Map<String, MutationComponent.ConfigurationComponent> pseudoMutmap = new HashMap<>(mutmap);
        pseudoMutmap.put(mutation.key(), new MutationComponent.ConfigurationComponent(ImmutableMap.copyOf(pseudoConfmap)));
        stack.set(DataComponentTypes.MUTATION_COMPONENT_TYPE, new MutationComponent(ImmutableMap.copyOf(pseudoMutmap)));
    }

    public void addWithCraft(Mutations mutation) {
        if(has(mutation)) {
            return;
        }

        Map<String, Integer> pseudoConfmap = new HashMap<>();
        mutation.parameters().forEach(parameter -> {
            pseudoConfmap.put(parameter.key(), DEFAULT_VALUE);
        });

        Map<String, MutationComponent.ConfigurationComponent> pseudoMutmap = new HashMap<>(mutmap);
        pseudoMutmap.put(mutation.key(), new MutationComponent.ConfigurationComponent(ImmutableMap.copyOf(pseudoConfmap)));
        stack.set(DataComponentTypes.MUTATION_COMPONENT_TYPE, new MutationComponent(ImmutableMap.copyOf(pseudoMutmap)));

        mutation.ability().onCraft(stack);
    }

    public void remove(Mutations mutation) {
        if (!has(mutation)) {
            return;
        }

        Map<String, MutationComponent.ConfigurationComponent> pseudo = new HashMap<>(mutmap);
        pseudo.remove(mutation.key());
        stack.set(DataComponentTypes.MUTATION_COMPONENT_TYPE, new MutationComponent(ImmutableMap.copyOf(pseudo)));

        mutation.ability().decraft(stack);
    }

    public void configure(Mutations.Parameters parameter, int value) {
        Mutations mutation = Mutations.byParameter(parameter);

        if(!has(mutation)) {
            return;
        }

        Map<String, MutationComponent.ConfigurationComponent> pseudoMutmap = new HashMap<>(mutmap);
        Map<String, Integer> pseudoConfmap = new HashMap<>(pseudoMutmap.get(mutation.key()).confmap());

        pseudoConfmap.put(parameter.key(), value);

        pseudoMutmap.put(mutation.key(), new MutationComponent.ConfigurationComponent(ImmutableMap.copyOf(pseudoConfmap)));
        stack.set(DataComponentTypes.MUTATION_COMPONENT_TYPE, new MutationComponent(ImmutableMap.copyOf(pseudoMutmap)));
    }

    public void nextConfval(Mutations.Parameters parameter, Level level) {
        Mutations mutation = Mutations.byParameter(parameter);

        if(!has(mutation)) {
            return;
        }

        Map<String, MutationComponent.ConfigurationComponent> pseudoMutmap = new HashMap<>(mutmap);
        Map<String, Integer> pseudoConfmap = new HashMap<>(pseudoMutmap.get(mutation.key()).confmap());

        pseudoConfmap.put(
            parameter.key(),
            parameter.next(pseudoConfmap.get(parameter.key()))
        );

        pseudoMutmap.put(mutation.key(), new MutationComponent.ConfigurationComponent(ImmutableMap.copyOf(pseudoConfmap)));
        stack.set(DataComponentTypes.MUTATION_COMPONENT_TYPE, new MutationComponent(ImmutableMap.copyOf(pseudoMutmap)));

        mutation.ability().onUpdate(stack, level);
    }

    public void nextConfVal(String key, Level level) {
        nextConfval(Mutations.Parameters.byKey(key), level);
    }

    public void prevConfigVal(Mutations.Parameters parameter, Level level) {
        Mutations mutation = Mutations.byParameter(parameter);

        if(!has(mutation)) {
            return;
        }

        Map<String, MutationComponent.ConfigurationComponent> pseudoMutmap = new HashMap<>(mutmap);
        Map<String, Integer> pseudoConfmap = new HashMap<>(pseudoMutmap.get(mutation.key()).confmap());

        pseudoConfmap.put(
            parameter.key(),
            parameter.prev(pseudoConfmap.get(parameter.key()))
        );

        pseudoMutmap.put(mutation.key(), new MutationComponent.ConfigurationComponent(ImmutableMap.copyOf(pseudoConfmap)));
        stack.set(DataComponentTypes.MUTATION_COMPONENT_TYPE, new MutationComponent(ImmutableMap.copyOf(pseudoMutmap)));

        mutation.ability().onUpdate(stack, level);
    }

    public void prevConfigVal(String key, Level level) {
        prevConfigVal(Mutations.Parameters.byKey(key), level);
    }

    public void resetConfigVal(Mutations.Parameters parameter, Level level) {
        Mutations mutation = Mutations.byParameter(parameter);

        if(!has(mutation)) {
            return;
        }

        Map<String, MutationComponent.ConfigurationComponent> pseudoMutmap = new HashMap<>(mutmap);
        Map<String, Integer> pseudoConfmap = new HashMap<>(pseudoMutmap.get(mutation.key()).confmap());

        pseudoConfmap.put(
            parameter.key(),
            parameter.head()
        );

        pseudoMutmap.put(mutation.key(), new MutationComponent.ConfigurationComponent(ImmutableMap.copyOf(pseudoConfmap)));
        stack.set(DataComponentTypes.MUTATION_COMPONENT_TYPE, new MutationComponent(ImmutableMap.copyOf(pseudoMutmap)));

        mutation.ability().onUpdate(stack, level);
    }

    public void resetConfigVal(String key, Level level) {
        resetConfigVal(Mutations.Parameters.byKey(key), level);
    }

    public int getConfigVal(Mutations.Parameters parameter) {
        Mutations mutation = Mutations.byParameter(parameter);

        if (!has(mutation)) {
            return 0;
        }

        ImmutableMap<String, MutationComponent.ConfigurationComponent> currentMutmap = stack.get(DataComponentTypes.MUTATION_COMPONENT_TYPE).mutmap();

        int configVal = currentMutmap.get(mutation.key()).confmap().get(parameter.key());
        return configVal;
    }

    public int getKills() {
        if(!has(Mutations.INSATIABLE_VORACITY)) {
            return 0;
        }

        return stack.getOrDefault(DataComponentTypes.VORACITY_COMPONENT_TYPE, 0);
    }

    public void increaseKill() {
        if(!has(Mutations.INSATIABLE_VORACITY)) {
            return;
        }

        int newKillsNumber = getKills() + 1;
        stack.set(DataComponentTypes.VORACITY_COMPONENT_TYPE, newKillsNumber);
    }

    public ItemStack getStack() {
        return stack;
    }

    public List<String> getDisplayNames() {
        List<String> displayNames = new ArrayList<>();

        for (String key : mutmap.keySet()) {
            Mutations pseudo = Mutations.byKey(key);

            if (pseudo != null) {
                displayNames.add(pseudo.title());
            }
        }

        return displayNames;
    }

    public List<Mutations.Parameters> getAllParameters() {
        Set<Mutations> muts = matchMutations();
        List<Mutations.Parameters> parameters = new ArrayList<>();

        for (var mut : muts) {
            for (var param : mut.parameters()) {
                parameters.add(param);
            }
        }

        return parameters;
    }

    public Set<Mutations> matchMutations() {
        MutationComponent component = stack.get(DataComponentTypes.MUTATION_COMPONENT_TYPE);

        Set<String> mutationKeys = new HashSet<>(component.mutmap().keySet());

        Set<Mutations> muts = new HashSet<>();

        mutationKeys.forEach(
            (key) -> muts.add(Mutations.byKey(key))
        );

        return muts;
    }
}
