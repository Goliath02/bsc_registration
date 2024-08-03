<script>

export default {
	name: "IBANInput",
	computed: {

		getIBAN() {
			return this.IBANSection1 + " " + this.IBANSection2 + " " + this.IBANSection3 + " " + this.IBANSection4 + " " + this.IBANSection5 + " " + this.IBANSection6;
		}

	},

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

	watch: {
		IBANSection1() {
			this.$emit('IBANChange', this.getIBAN);
		},

		IBANSection2() {
			this.$emit('IBANChange', this.getIBAN);
		},

		IBANSection3() {
			this.$emit('IBANChange', this.getIBAN);
		},
		IBANSection4() {
			this.$emit('IBANChange', this.getIBAN);
		},

		IBANSection5() {
			this.$emit('IBANChange', this.getIBAN);
		},

		IBANSection6() {
			this.$emit('IBANChange', this.getIBAN);
		},
	},

	methods: {
		focusNext(event, max) {
			if (event.target.value.length === max) {
				const nextElement = this.$refs?.[`input-${Number(event.target.dataset.index) + 1}`]
				if (nextElement) {
					nextElement.focus()
				}
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
        <div class="flex gap-[1em] -lg:gap-[0.5em]">
            <input ref="input-1" v-model="IBANSection1"
                   class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center" data-index="1"
                   @input="focusNext($event,4)"
                   maxlength="4"
                   @keydown="isNumber($event)">
            <input ref="input-2" v-model="IBANSection2"
                   class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center" data-index="2"
                   maxlength="4"
                   @input="focusNext($event,4)"
                   @keydown="isNumber($event)">
            <input ref="input-3" v-model="IBANSection3"
                   class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center" data-index="3"
                   maxlength="4"
                   @input="focusNext($event,4)"
                   @keydown="isNumber($event)">
            <input ref="input-4" v-model="IBANSection4"
                   class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center" data-index="4"
                   maxlength="4"
                   @input="focusNext($event,4)"
                   @keydown="isNumber($event)">
            <input ref="input-5" v-model="IBANSection5"
                   class="w-full flex-2 h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center" data-index="5"
                   maxlength="4"
                   @input="focusNext($event,4)"
                   @keydown="isNumber($event)">

            <input ref="input-6" v-model="IBANSection6"
                   class="w-1/2 flex-2  h-[3em] lg:px-[0.5em]  bg-[#585858] rounded-lg font-medium appearance-none text-center" data-index="6"
                   maxlength="2"
                   @input="focusNext($event,2)"
                   @keydown="isNumber($event)">

        </div>
    </div>


</template>

<style scoped>

</style>