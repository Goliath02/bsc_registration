<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
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
    DefaultRegistration, Bsc_header, GenderSelection, FormHeader, BSCBanner, GrundAuwahl, PeronenAuswahl
  },
}

</script>

<template>
  <main class="flex flex-1 justify-center items-center  lg:px-[7%] lg:py-[2%] min-h-0 ">
    <div class="w-full h-full bg-bsc-gray rounded-lg overflow-hidden flex min-h-0">

      <b-s-c-banner class="-lg:hidden"></b-s-c-banner>

      <div class=" text-white flex-1 overflow-y-hidden flex flex-col">

        <router-view></router-view>

        <registration-navigation class="sticky bottom-0 bg-bsc-gray"/>

      </div>
    </div>
  </main>
</template>

<style>
.slide-enter-active, .slide-leave-active {
  transition: all 0.5s ease;
}

.slide-enter, .slide-leave-to /* .slide-leave-active in <2.1.8 */
{
  transform: translateX(100%);
  opacity: 0;
}

@keyframes opacityFromZero {
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
}

</style>