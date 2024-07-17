<script setup>

import Header from "@/components/BSC_Header.vue";
</script>

<template>
  <div class="bg-black h-screen  flex flex-col ">
    <Header></Header>
    <div class="flex flex-1 px-[15%] py-[2%] ">
      <div class="flex-1 rounded-[40px] overflow-hidden  bg-bsc-gray ">
        <RouterView></RouterView>
      </div>
    </div>


  </div>

</template>

<style scoped>
body {
  background-color: #000000;
}
</style>
