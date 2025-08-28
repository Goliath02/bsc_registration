<script setup lang="ts">
import { onMounted, onUnmounted, ref } from "vue";
import { Avatar, Button, Drawer } from "primevue";

const isExpanded = ref(true);
const isMobile = ref(false);

const toggleSidebar = () => {
  isExpanded.value = !isExpanded.value;
};

const checkIsMobile = () => {
  isMobile.value = window.innerWidth < 768; // md breakpoint
  if (isMobile.value) {
    isExpanded.value = false;
  }
};

onMounted(() => {
  checkIsMobile();
  window.addEventListener("resize", checkIsMobile);
});

onUnmounted(() => {
  window.removeEventListener("resize", checkIsMobile);
});

const visible = ref(false);

const navigationItems = [
  { icon: "pi pi-home", label: "Dashboard", route: "/dashboard" },
  { icon: "pi pi-calendar", label: "Courses", route: "/courses" },
  { icon: "pi pi-map-marker", label: "TrainingPlaces", route: "/places" },
  { icon: "pi pi-user-plus", label: "Trainer", route: "/trainer" },
  { icon: "pi pi-users", label: "Members", route: "/members" },
  { icon: "pi pi-user", label: "Profil", route: "/profile" },
  { icon: "pi pi-cog", label: "Einstellungen", route: "/settings" },
];
</script>

<template>
  <!-- Mobile App Bar -->
  <div
    v-if="isMobile"
    class="left-0 right-0 bg-surface-900 text-surface-0 h-16 py-2 flex items-center px-4 z-50 sticky top-0"
  >
    <Drawer v-model:visible="visible">
      <template #container="{ closeCallback }">
        <div class="flex flex-col h-full">
          <div class="flex items-center justify-between px-6 pt-4 shrink-0">
            <div class="flex items-center gap-2">
              <img class="h-8" src="@/assets/logo.svg" alt="Logo" />
              <h2 class="font-bold italic text-2xl">1.BSC</h2>
            </div>
            <span>
              <Button
                type="button"
                @click="closeCallback"
                icon="pi pi-times"
                class="!bg-white !border-white"
                rounded
              ></Button>
            </span>
          </div>
          <div class="overflow-y-auto">
            <nav class="flex-1 overflow-y-auto">
              <ul class="py-4">
                <li v-for="item in navigationItems" :key="item.route">
                  <router-link
                    :to="item.route"
                    class="flex items-center px-4 py-3 text-surface-0 hover:bg-surface-700 transition-colors"
                  >
                    <i :class="[item.icon, 'text-xl']" />
                    <span class="ml-4 transition-opacity">
                      {{ item.label }}
                    </span>
                  </router-link>
                </li>
              </ul>
            </nav>
          </div>
          <div class="mt-auto">
            <hr
              class="mb-4 mx-4 border-t border-0 border-surface-200 dark:border-surface-700"
            />
          </div>
        </div>
      </template>
    </Drawer>

    <div class="flex items-center justify-between w-full">
      <div class="flex items-center gap-2">
        <Button
          @click="visible = true"
          icon-only
          icon="pi pi-bars"
          severity="secondary"
          text
          class="text-surface-0 hover:bg-surface-700"
        />

        <div class="flex items-center gap-2">
          <img class="h-8" src="@/assets/logo.svg" alt="Logo" />
          <h2 class="font-bold italic text-2xl">1.BSC</h2>
        </div>
      </div>

      <Avatar label="K" class="mr-2" shape="circle" />
    </div>
  </div>

  <!-- Desktop Sidebar -->
  <div v-else class="flex md:h-screen md:flex-row">
    <div
      class="bg-surface-900 text-surface-0 transition-all duration-300 ease-in-out flex flex-col"
      :class="{ 'w-64': isExpanded, 'w-16': !isExpanded }"
    >
      <div
        class="p-4 flex items-center justify-between border-b border-surface-700 select-none"
      >
        <div v-if="isExpanded" class="flex flex-1 gap-1 justify-center">
          <img class="h-8" src="@/assets/logo.svg" alt="Logo" />
          <h2 class="flex-1 text-center font-bold italic text-2xl">1.BSC</h2>
        </div>
        <Button
          icon-only
          :icon="isExpanded ? 'pi pi-angle-left' : 'pi pi-angle-right'"
          severity="secondary"
          text
          @click="toggleSidebar"
          class="text-surface-0 hover:bg-surface-700"
        />
      </div>

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
