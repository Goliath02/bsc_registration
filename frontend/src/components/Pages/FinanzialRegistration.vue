<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import FormHeader from "@/components/FormHeader.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import BscInput from "@/components/BasicRegistration/BSCInput.vue";
import IBANInput from "@/components/FinancesRegistration/IBANInput.vue";
import BSCCheckbox from "@/components/FinancesRegistration/BSCCheckbox.vue";
import DatenschutzCheckbox from "@/components/FinancesRegistration/DatenschutzCheckbox.vue";

export default {
  name: "FinanzialRegistration",
  components: {DatenschutzCheckbox, BSCCheckbox, IBANInput, BscInput, GenderSelection, GrundAuwahl, FormHeader, PeronenAuswahl}
}
</script>

<template>


  <FormHeader/>

  <div class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em]">
    <div class="flex gap-[1em]">
      <bsc-input header-field="Bankname" input-type="text"/>
      <bsc-input header-field="Bankort" input-type="text"/>
    </div>

    <i-b-a-n-input/>

    <div>
      <bsc-input header-field="BIC" input-type="text"/>
    </div>

    <div class="flex gap-[1em]">
      <bsc-input class="flex-1" header-field="Vorname des Kontoinhabers" input-type="text"/>
      <bsc-input class="flex-1" header-field="Nachname des Kontoinhabers" input-type="text"/>
    </div>


    <datenschutz-checkbox/>
    <b-s-c-checkbox label-text="Hiermit bestätige ich, dass alle angegebenen Daten der richtigkeit ensprechen."/>

  </div>

</template>

<style scoped>

</style>