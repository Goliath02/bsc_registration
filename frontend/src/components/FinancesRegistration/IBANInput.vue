<script>

import {useRegistrationStore} from "@/stores/RegistrationStore.js";

export default {
  name: "IBANInput",
  computed: {

    isValidGermanIBAN() {

      const s = [this.IBANSection2, this.IBANSection3, this.IBANSection4, this.IBANSection5, this.IBANSection6, this.IBANSection1].join('')
          .toUpperCase()
          .replace("D", "13")
          .replace("E", "14");

      const validIbanLength = (this.IBANSection1 + this.IBANSection2 + this.IBANSection3 + this.IBANSection4 + this.IBANSection5 + this.IBANSection6).length === 22;

      let validIban;

      const regex = new RegExp(/\D+/);

      if (regex.test(s) || s.length === 0) {
        validIban = false;
      } else {
        validIban = BigInt(s) % 97n === BigInt(1);
      }

      useRegistrationStore().isFilled.financialData.iban = validIban && validIbanLength;

      return useRegistrationStore().triedToValidateFinancialForm && (!validIban || !validIbanLength);
    },
  },

  props: {
    isNotValid: Boolean
  },

  created() {
    this.setIbanIfAlreadyTyped();
  },

  data() {
    return {
      IBANSection1: null,
      IBANSection2: null,
      IBANSection3: null,
      IBANSection4: null,
      IBANSection5: null,
      IBANSection6: null,
    }
  },

  methods: {

    setIbanIfAlreadyTyped() {
      const currentInputIban = useRegistrationStore().registrationData.financial.iban;

      if (currentInputIban) {
        let slicedIban = [];

        for (let i = 0; slicedIban.length !== 6; i += 4) {
          slicedIban.push(currentInputIban.slice(i, i + 4));
        }

        this.IBANSection1 = slicedIban[0];
        this.IBANSection2 = slicedIban[1];
        this.IBANSection3 = slicedIban[2];
        this.IBANSection4 = slicedIban[3];
        this.IBANSection5 = slicedIban[4];
        this.IBANSection6 = slicedIban[5];
      }


    },

    getIBAN() {
      return [this.IBANSection1, this.IBANSection2, this.IBANSection3, this.IBANSection4, this.IBANSection5, this.IBANSection6].join('');
    },

    onIbanInput(event, max) {
      this.postInput(event.data);

      if (event.target.value && event.target.value.length < max) {

      } else {
        event.target.value = event.target.value.substring(0, max);
        this.focusNext(event, max);
      }
      this.fixPostInputSection();
      useRegistrationStore().registrationData.financial.iban = this.getIBAN();
    },

    postInput(input) {

      if (input != null && input.length > 4) {

        let slices = [];

        for (let i = 0; slices.length !== 6; i += 4) {
          slices.push(input.slice(i, i + 4));
        }

        slices[5] = slices[5].slice(0, 2);

        switch (slices.length) {

          case 6:
            this.IBANSection6 = slices[5];
          case 5:
            this.IBANSection5 = slices[4];
          case 4:
            this.IBANSection4 = slices[3];
          case 3:
            this.IBANSection3 = slices[2];
          case 2:
            this.IBANSection2 = slices[1];
          case 1:
            this.IBANSection1 = slices[0];
            break;

        }

      }
    },

    focusNext(event, max) {
      useRegistrationStore().updateFinancialValidation();
      if (event.target.value.length >= max) {
        const nextElement = this.$refs?.[`input-${Number(event.target.dataset.index) + 1}`]
        if (nextElement) {
          nextElement.focus()
        }
      }
    },

    fixPostInputSection() {
      this.IBANSection1 = this.IBANSection1.substring(0, 4);
    }
  }
}
</script>

<template>

  <div>
    <div class="font-bold text-[1.2em]">IBAN</div>
    <div class="flex gap-[1em] -lg:gap-[0.5em]">
      <input ref="input-1" v-model="IBANSection1"
             :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
             class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
             data-index="1"
             @input="onIbanInput($event,4)"
      >
      <input ref="input-2" v-model="IBANSection2"
             :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
             class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
             data-index="2"
             maxlength="4"
             @input="onIbanInput($event,4)"
      >
      <input ref="input-3" v-model="IBANSection3"
             :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
             class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
             data-index="3"
             maxlength="4"
             @input="onIbanInput($event,4)"
      >
      <input ref="input-4" v-model="IBANSection4"
             :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
             class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
             data-index="4"
             maxlength="4"
             @input="onIbanInput($event,4)"
      >
      <input ref="input-5" v-model="IBANSection5"
             :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
             class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
             data-index="5"
             maxlength="4"
             @input="onIbanInput($event,4)"
      >

      <input ref="input-6" v-model="IBANSection6"
             :class="isValidGermanIBAN ? 'border-2 border-red-700' : '' "
             class="w-1/2 flex-2  h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center"
             data-index="6"
             maxlength="2"
             @input="onIbanInput($event,2)"
      >

    </div>
  </div>

</template>

<style scoped>

</style>