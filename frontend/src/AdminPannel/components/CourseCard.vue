<script setup lang="ts">
import Card from "primevue/card";
import ProgressBar from "primevue/progressbar";

const props = defineProps({
	title: String,
	imagePath: String,
	trainer: String,
	status: String,
	fromDate: String,
	toDate: String,
	totalUnits: Number,
	unitsDone: Number,
	place: String,
	participants: Number,
	maxParticipants: Number
});
</script>

<template>

		<Card style="width: 25rem; overflow: hidden; "
		      class=" select-none h-min ">
			<template #header>
				<div :style="{ backgroundImage: `url(${imagePath})` }" class="h-[6em] bg-cover bg-center"/>
			</template>
			<template #title >
				<div class="flex items-center justify-between px-2 py-1 hover:bg-[#262626] rounded-lg transition-colors cursor-pointer">
					<h3 class="underline font-bold">{{title}}</h3>
					<div class="flex items-center gap-2">
						<p class="text-lg font-bold">{{status}}</p>
						<div class="w-4 h-4 rounded-full bg-green-500"></div>
					</div>

				</div>

			</template>
			<template #subtitle>

				<div class="flex items-center w-min px-2 gap-2 cursor-pointer hover:text-white transition-colors whitespace-nowrap">
					<div class="pi pi-user"></div>
					<div>{{trainer}}</div>
				</div>

			</template>
			<template #content>
				<div>

					<div class="flex flex-col gap-2 px-2">
						<div class="flex justify-between items-center font-bold">
							<div>{{fromDate}}</div>
							<div>{{toDate}}</div>
						</div>

						<div class="px-4 flex flex-col gap-1 align-center justify-center">
							<ProgressBar :value="2" style="--p-progressbar-border-radius: 15px; --p-progressbar-label-color: transparent"/>
							<h5 class="font-bold text-center">{{ unitsDone }}/{{ totalUnits }} Einheiten</h5>
						</div>
					</div>

				</div>
			</template>
			<template #footer>
				<div class="flex justify-between items-center font-bold px-2">

					<div class="flex items-center gap-2 cursor-pointer border border-transparent hover:bg-[#262626] px-2 py-2 rounded-lg transition-colors">
						<div style="font-size: 1.2em" class="pi pi-map-marker"></div>
						<h5>{{place}}</h5>
					</div>

					<div class="flex items-center gap-2 font-bold cursor-pointer border border-transparent hover:bg-[#262626] px-2 py-2 rounded-lg transition-colors">
						<div style="font-size: 1.2em" class="pi pi-users"></div>
						<h5>{{ participants }}/ {{ maxParticipants }} Teilnehmer</h5>
					</div>
				</div>
			</template>
		</Card>

</template>

<style scoped>

</style>