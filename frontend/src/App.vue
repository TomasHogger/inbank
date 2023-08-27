<template>
  <form class="form" @submit.prevent="submitForm">
    <label for="personalCode">Personal code:</label>
    <input id="personalCode" v-model="loanRequest.personalCode" required type="text"><br>

    <label for="loanAmount">Amount:</label>
    <input id="loanAmount" v-model="loanRequest.loanAmount" max="10000" min="2000" required type="number"><br>

    <label for="monthPeriod">Months:</label>
    <input id="name" v-model="loanRequest.monthPeriod" max="60" min="12" required type="number"><br>

    <button type="submit">Check loan</button>

    <p v-show="loanResponse">Allowed: {{ loanResponse?.approved }}</p>
    <p v-show="loanResponse?.maxAmount">Amount: {{ loanResponse?.maxAmount }}</p>
    <p v-show="error">Error: {{ error?.message }}</p>
  </form>
</template>

<script lang="ts">
import {checkLoanAvailability} from "@api";
import type {LoadRequest, LoadResponse} from "@/dto";

export default {
  data() {
    return {
      loanRequest: {
        personalCode: '',
        loanAmount: 2000,
        monthPeriod: 12
      } as LoadRequest,
      loanResponse: null as null | LoadResponse,
      error: null as null | Error
    };
  },
  methods: {
    submitForm() {
      this.error = null;
      this.loanResponse = null;
      checkLoanAvailability(this.loanRequest)
          .then((resp: LoadResponse) => this.loanResponse = resp)
          .catch((error: Error) => this.error = error)
    }
  }
};
</script>

<style scoped>
form {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  background-color: #f5f5f5;
  border: 1px solid #ddd;
  border-radius: 5px;
}

label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}

input {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 3px;
  box-sizing: border-box;
}

button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 3px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

button:hover {
  background-color: #0056b3;
}
</style>