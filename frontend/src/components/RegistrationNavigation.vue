<script>
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";

export default {
  name: "RegistrationNavigation",

  data() {
    return {
      pages: [DefaultRegistration, FinanzialRegistration],
      activePage: {}
    }
  },


}
</script>

<template>

  <div class="flex p-[1em]">

    <div class="basis-1/3 ">
      <router-link to="/">
        <button class="font-bold  px-[1em] py-[0.5em] rounded">Zurück</button>
      </router-link>
    </div>

    <div class="flex basis-1/3 justify-center items-center gap-[1em]">
      <div v-for="page in pages" :class="{'!bg-white' : $route.name === page.name}"
           class="w-[0.8em] h-[0.8em] bg-stone-500 rounded-full"></div>
    </div>

    <div class="basis-1/3 flex justify-end">
      <router-link to="/kontodaten">
        <button class="font-bold bg-red-600 px-[1em] py-[0.5em] rounded">Weiter</button>
      </router-link>
    </div>


  </div>
</template>

<style scoped>

</style>