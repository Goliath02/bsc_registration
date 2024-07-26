<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
import TextInput from "@/components/BasicRegistration/BSCInput.vue";
import BSCBanner from "@/components/BSCBanner.vue";
import FormHeader from "@/components/FormHeader.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import Bsc_header from "@/components/BSC_Header.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";
import RegistrationNavigation from "@/components/RegistrationNavigation.vue";
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";

export default {
  name: "MainPage",
  components: {
    FinanzialRegistration,
    RegistrationNavigation,
    DefaultRegistration, Bsc_header, GenderSelection, FormHeader, BSCBanner, TextInput, GrundAuwahl, PeronenAuswahl
  }
}
</script>

<template>
  <main class="flex flex-1 justify-center items-center  px-[7%] py-[2%] min-h-0 ">
    <div class="w-full h-full bg-bsc-gray rounded-lg overflow-hidden flex min-h-0">


      <b-s-c-banner class="-lg:hidden"></b-s-c-banner>

      <div class=" text-white flex-1 overflow-y-hidden flex flex-col">

        <!--        //TODO--> Make Transition on Nested routerview possible

        <router-view v-slot="{ Component }">
          <transition name="slide-fade">
            <component :is="Component"/>
          </transition>
        </router-view>

        <registration-navigation class="sticky bottom-0 bg-bsc-gray"/>


      </div>
    </div>
  </main>
</template>

<style>
/*
  Enter and leave animations can use different
  durations and timing functions.
*/
.slide-fade-enter-active {
  transition: all 0.3s ease-out;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter-from,
.slide-fade-leave-to {
  transform: translateX(20px);
  opacity: 0;
}
</style>