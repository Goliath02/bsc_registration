<script>
import PeronenAuswahl from "@/components/BasicRegistration/PeronenAuswahl.vue";
import FormHeader from "@/components/FormHeader.vue";
import GrundAuwahl from "@/components/BasicRegistration/GrundAuwahl.vue";
import GenderSelection from "@/components/BasicRegistration/GenderSelection.vue";
import BscInput from "@/components/BasicRegistration/BSCInput.vue";
import IBANInput from "@/components/FinancesRegistration/IBANInput.vue";
import BSCCheckbox from "@/components/FinancesRegistration/BSCCheckbox.vue";
import DatenschutzCheckbox from "@/components/FinancesRegistration/DatenschutzCheckbox.vue";
import ReworkedBSCInput from "@/components/BasicRegistration/ReworkedBSCInput.vue";

export default {
  name: "FinanzialRegistration",
  components: {
    ReworkedBSCInput,
    DatenschutzCheckbox, BSCCheckbox, IBANInput, BscInput, GenderSelection, GrundAuwahl, FormHeader, PeronenAuswahl
  },
  data() {
    return {
      financial: {
        bankName: "",
        bankPlace: "",
        bankOwnerName: "",
        bankOwnerSureName: "",
      }
    }
  }
}
</script>

<template>


  <FormHeader/>

  <div class="flex flex-1 flex-col gap-[1em] max-h-[65vh] overflow-y-auto px-[2em] py-[1em]">
    <div class="flex gap-[1em]">


      <reworked-b-s-c-input v-model="financial.bankName" header-field="Bankname"/>
      <reworked-b-s-c-input v-model="financial.bankPlace" header-field="Bankort"/>
    </div>

    <i-b-a-n-input/>

    <div>
      <bsc-input header-field="BIC" input-type="text"/>
    </div>

    <div class="flex gap-[1em]">


      <reworked-b-s-c-input v-model="financial.bankOwnerName" header-field="Vorname des Kontoinhabers"/>
      <reworked-b-s-c-input v-model="financial.bankOwnerSureName" header-field="Nachname des Kontoinhabers"/>
    </div>


    <datenschutz-checkbox/>
    <b-s-c-checkbox label-text="Hiermit bestätige ich, dass alle angegebenen Daten der richtigkeit ensprechen."/>

  </div>

</template>

<style scoped>

</style>