package me.shafran.actiontracker.di

import android.content.Context
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.registries.FactoryRegistryLocator
import toothpick.registries.MemberInjectorRegistryLocator

fun initializeToothpick(context: Context, isProduction: Boolean) {
    setConfiguration(isProduction)
    setRegistry()
    openRootScope(context)
}

private fun setConfiguration(isProduction: Boolean) {
    if (isProduction) {
        Toothpick.setConfiguration(
                Configuration.forProduction()
                        .disableReflection()
        )
    } else {
        Toothpick.setConfiguration(
                Configuration
                        .forDevelopment()
                        .preventMultipleRootScopes()
                        .disableReflection()
        )
    }
}

private fun setRegistry() {
    FactoryRegistryLocator.setRootRegistry(me.shafran.actiontracker.FactoryRegistry())
    MemberInjectorRegistryLocator.setRootRegistry(me.shafran.actiontracker.MemberInjectorRegistry())
}

private fun openRootScope(context: Context) {
    Toothpick
            .openScope(Scopes.ROOT_SCOPE)
            .installModules(
                    RootModule(context),
                    DataModule(),
                    NavigationModule()
            )
}
