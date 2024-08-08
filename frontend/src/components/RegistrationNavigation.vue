<script>
import FinanzialRegistration from "@/components/Pages/FinanzialRegistration.vue";
import DefaultRegistration from "@/components/Pages/DefaultRegistration.vue";
import {useRegistrationStore} from "@/stores/RegistrationStore.js";
import router from "@/router.js";

export default {
	name: "RegistrationNavigation",
	methods: {
		useRegistrationStore,

		routerToNextSite() {

			if (useRegistrationStore().isFormCorrect()) {
				router.push("/kontodaten");
			} else {
				console.log("Form is not correct");
			}

		},

	},

	data() {
		return {
			pages: [DefaultRegistration, FinanzialRegistration],
			activePage: {}
		}
	},
	computed: {

		isFirstPage() {
			return this.$route.name === this.pages[0].name;
		},

		isLastPage() {
			return this.$route.name === this.pages[this.pages.length - 1].name;
		},

		getLastPageButtonText() {
			return this.$route.name === this.pages[this.pages.length - 1].name ? "Abschicken" : "Weiter";
		}

	}

}
</script>

<template>

	<div class="flex p-[1em]">

		<div :class="{ 'invisible': isFirstPage}" class="basis-1/3 ">
			<router-link to="/">
				<button class="font-bold  px-[1em] py-[0.5em] rounded">Zurück</button>
			</router-link>
		</div>

		<div class="flex basis-1/3 justify-center items-center gap-[1em]">
			<div v-for="page in pages" :class="{'!bg-white' : $route.name === page.name}"
			     class="w-[0.8em] h-[0.8em] bg-stone-500 rounded-full transition-colors dot"></div>
		</div>

		<div class="basis-1/3 flex justify-end">
			<button v-if="!isLastPage" class="font-bold bg-red-600 px-[1em] py-[0.5em] rounded" @click="routerToNextSite">
				{{ getLastPageButtonText }}
			</button>
			<button v-else class="font-bold bg-red-600 px-[1em] py-[0.5em] rounded" @click="useRegistrationStore().postData()">
				{{ getLastPageButtonText }}
			</button>

		</div>

	</div>
</template>

<style scoped>

.dot {
    transition-property: color, background-color, border-color, text-decoration-color, fill, stroke;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 150ms;
}

</style>