package net.engineeringdigest.journalApp.services;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import net.engineeringdigest.journalApp.entity.User;

import java.lang.reflect.InvocationTargetException;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {

    public UserArgumentsProvider() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception{
        return Stream.of(
                Arguments.of(User.builder().userName("dvsdd").password("gio").build(),true),
                Arguments.of(User.builder().userName("bbdsda").password("fjvk").build(),true)
        );
    }

    


}
