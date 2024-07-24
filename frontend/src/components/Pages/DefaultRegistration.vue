<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
import BscInput from "@/components/BasicRegistration/BSCInput.vue";
import FormHeader from "@/components/FormHeader.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";

export default {
  name: "DefaultRegistration",
  components: {GenderSelection, FormHeader, BscInput, GrundAuwahl, PeronenAuswahl}
}
</script>

<template>

  <FormHeader/>

  <div class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em] ">
    <PeronenAuswahl/>
    <GrundAuwahl/>

    <div class="flex gap-[1.5em]">
      <bsc-input header-field="Vorname" input-type="text"/>
      <bsc-input header-field="Nachname" input-type="text"/>
    </div>

    <div class="flex gap-[1.5em]">
      <bsc-input header-field="Geburtsdatum" input-type="date" class="flex-1 "/>
      <gender-selection class="flex-1 w-1/2"/>
    </div>

    <div class="flex gap-[1.5em]">
      <bsc-input header-field="Email" input-type="email"/>
      <bsc-input header-field="Telefon/Mobil" input-type="text"/>
    </div>

    <div class="flex gap-[1.5em]">
      <bsc-input header-field="Straße" input-type="text"/>
      <bsc-input header-field="PLZ" input-type="text"/>

    </div>

    <bsc-input header-field="Ort" input-type="text"/>

  </div>


</template>

<style scoped>

</style>