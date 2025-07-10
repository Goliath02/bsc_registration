<script setup lang="ts">
import { ref } from 'vue';
import { Button } from 'primevue';

const isExpanded = ref(true);

const toggleSidebar = () => {
    isExpanded.value = !isExpanded.value;
};

// Beispiel-Navigation-Items
const navigationItems = [
    { icon: 'pi pi-home', label: 'Dashboard', route: '/dashboard' },
    { icon: 'pi pi-user', label: 'Profil', route: '/profile' },
    { icon: 'pi pi-cog', label: 'Einstellungen', route: '/settings' },
    // Weitere Menüpunkte hier hinzufügen
];
</script>

<template>
    <div class="flex h-screen">
        <!-- Seitenleiste -->
        <div
            class="bg-surface-900 text-surface-0 transition-all duration-300 ease-in-out flex flex-col"
            :class="{ 'w-64': isExpanded, 'w-16': !isExpanded }"
        >
            <!-- Header der Seitenleiste -->
            <div class="p-4 flex items-center justify-between border-b border-surface-700">
                <img
                    v-if="isExpanded"
                    class="h-8"
                    src="@/assets/logo.svg"
                    alt="Logo"
                />
                <Button
                    icon-only
                    :icon="isExpanded ? 'pi pi-angle-left' : 'pi pi-angle-right'"
                    severity="secondary"
                    text
                    @click="toggleSidebar"
                    class="text-surface-0 hover:bg-surface-700"
                />
            </div>

            <!-- Navigation -->
            <nav class="flex-1 overflow-y-auto">
                <ul class="py-4">
                    <li v-for="item in navigationItems" :key="item.route">
                        <router-link
                            :to="item.route"
                            class="flex items-center px-4 py-3 text-surface-0 hover:bg-surface-700 transition-colors"
                        >
                            <i :class="[item.icon, 'text-xl']"></i>
                            <span
                                v-if="isExpanded"
                                class="ml-4 transition-opacity"
                                :class="{ 'opacity-0': !isExpanded }"
                            >
                                {{ item.label }}
                            </span>
                        </router-link>
                    </li>
                </ul>
            </nav>

            <!-- Footer der Seitenleiste -->
            <div class="p-4 border-t border-surface-700">
                <div class="flex items-center">
                    <i class="pi pi-user text-xl"></i>
                    <span
                        v-if="isExpanded"
                        class="ml-4 transition-opacity"
                        :class="{ 'opacity-0': !isExpanded }"
                    >
                        Benutzername
                    </span>
                </div>
            </div>
        </div>

    </div>
</template>

<style scoped>
</style>