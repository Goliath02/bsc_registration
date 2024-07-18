<script>
import PeronenAuswahl from "@/components/PeronenAuswahl.vue";
import GrundAuwahl from "@/components/GrundAuwahl.vue";
import TextInput from "@/components/BSCInput.vue";
import BSCBanner from "@/components/BSCBanner.vue";
import FormHeader from "@/components/FormHeader.vue";
import GenderSelection from "@/components/GenderSelection.vue";

export default {
  name: "MainPage",
  components: {GenderSelection,  FormHeader, BSCBanner, TextInput, GrundAuwahl, PeronenAuswahl}
}
</script>

<template>
  <div class="h-full flex text-white  ">

    <BSCBanner></BSCBanner>

    <div class=" flex-1 ">

      <FormHeader></FormHeader>
      <div class="p-[1em] overflow-scroll" >

        <PeronenAuswahl></PeronenAuswahl>
        <GrundAuwahl></GrundAuwahl>

        <div class="flex gap-[1em]">
          <text-input header-field="Vorname" input-type="text"></text-input>
          <text-input header-field="Nachame" input-type="text"></text-input>
        </div>

        <div class="flex gap-[1em]">
          <text-input header-field="Geburtsdatum" input-type="date"></text-input>
          <GenderSelection></GenderSelection>
        </div>

        <div class="flex gap-[1em]">
          <text-input class="flex-1" header-field="Email" input-type="email"></text-input>
          <text-input class="flex-2" header-field="Telefon/Mobil" input-type="text"></text-input>
        </div>


        <div class="flex gap-[1em]">
          <text-input class="flex-1" header-field="Straße" input-type="text"></text-input>
          <text-input class="flex-2" header-field="PLZ" input-type="text"></text-input>
        </div>

        <div>
          <text-input class="w-1/2" header-field="Ort" input-type="text"></text-input>
        </div>

      </div>
    </div>


  </div>
</template>

<style scoped>

</style>