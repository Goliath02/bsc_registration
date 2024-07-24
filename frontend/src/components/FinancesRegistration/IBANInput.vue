<script>

export default {
  name: "IBANInput",
  computed: {},


  data() {
    return {
      IBANSection1: "",
      IBANSection2: "",
      IBANSection3: "",
      IBANSection4: "",
      IBANSection5: "",
      IBANSection6: "",
    }
  },

  methods: {
    focusNext(event, max) {
      if (event.target.value.length === max) {
        const nextElement = this.$refs?.[`input-${Number(event.target.dataset.index) + 1}`]
        if (nextElement) nextElement.focus()
      }
    },

    isNumber(evt) {
      evt = (evt) ? evt : window.event;
      let charCode = (evt.which) ? evt.which : evt.keyCode;
      if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
        evt.preventDefault();
      } else {
        return true;
      }
    }
  }
}
</script>

<template>

  <div>
    <div class="font-bold text-[1.2em]">IBAN</div>
    <div class="flex gap-[1em] ">
      <input v-model="IBANSection1" @keydown="isNumber($event)" data-index="1" ref="input-1" @input="focusNext($event,4)" maxlength="4"
             class="w-full flex-2 h-[3em] px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none">
      <input v-model="IBANSection2" @keydown="isNumber($event)" data-index="2" ref="input-2" @input="focusNext($event,4)" maxlength="4"
             class="w-full flex-2 h-[3em] px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none">
      <input v-model="IBANSection3" @keydown="isNumber($event)" data-index="3" ref="input-3" @input="focusNext($event,4)" maxlength="4"
             class="w-full flex-2 h-[3em] px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none">
      <input v-model="IBANSection4" @keydown="isNumber($event)" data-index="4" ref="input-4" @input="focusNext($event,4)" maxlength="4"
             class="w-full flex-2 h-[3em] px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none">
      <input v-model="IBANSection5" @keydown="isNumber($event)" data-index="5" ref="input-5" @input="focusNext($event,4)" maxlength="4"
             class="w-full flex-2 h-[3em] px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none">

      <input v-model="IBANSection6" @keydown="isNumber($event)" data-index="6" ref="input-6" maxlength="2" @input="focusNext($event,2)"
             class="w-1/2 flex-2  h-[3em] px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none">

    </div>
  </div>


</template>

<style scoped>

</style>