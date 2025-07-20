<script setup lang="ts">

import {ref} from "vue";
import CourseTimeFrameInfo from "@/AdminPannel/components/CourseTimeFrameInfo.vue";

const open = defineModel<boolean>({required: true})

const courseTitle = ref('');
const courseType = ref('');
const startDate = ref();
const trainingUnits = ref();

const days = ref([]);
const times = ref([]);

</script>

<template>
<Dialog v-model:visible="open" modal :closable=false close-on-escape :draggable=false :show-header=false class="p-4">

	<div class="flex flex-col gap-4 justify-center items-center w-full">
		<h1 class="text-4xl font-bold">Add course</h1>

		<InputText v-model="courseTitle" placeholder="Course title" class="w-full" />
		<Select class="w-full" v-model="courseType" placeholder="Course type" :options="['Not Swimmer', 'Swimmer', 'Aqua-Gymnastics']" />

		<div class="flex gap-4 md:flex-row flex-col w-full">
			<DatePicker v-model="startDate" showIcon fluid :showOnFocus="false" showTime hourFormat="24" placeholder="Start date" dateFormat="dd.mm.yy"  />
			<InputNumber v-model="trainingUnits" placeholder="Training units" />
		</div>

		<CourseTimeFrameInfo v-model="startDate"/>


		<div class="flex gap-4 w-full justify-center items-center">
			<Button @click="open = false" class="px-4 py-2">Back</Button>
			<Button @click="open = false" class="px-4 py-2">Save</Button>
		</div>

	</div>





</Dialog>
</template>

<style scoped>

</style>