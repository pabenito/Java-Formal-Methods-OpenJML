# Open JML Notes

## Info

- [Web page](https://www.openjml.org/)
- [GitHub](https://github.com/OpenJML/OpenJML)

## What is OpenJML?

OpenJML is a **deductive verification** tool for **Java** to check if the **implementation is consistent with the specifications** of programs **annotated in the Java Modeling Language** (JML).

## What is OpenJML capable of?

OpenJML is a command-line tool that has the following capabilities:
  
  - Parsing and type-checking of Java+JML programs
  - Static checking of JML annotations using backend SMT solvers
  - Runtime-assertion checking

Other features that are in development include

  - Integration with IDEs like IntelliJ and Eclipse
  - Generation of javadoc documentation embellished with JML information
  - Generation of annotation templates
  - Inference of specifications from implementations
  - Auto-generation of test cases using symbolic execution, guided by JML annotations.

## OpenJML verification types support

OpenJML support Deductive Verification (DV) and Runtime-assertion-checking (RAC). But most of the support is for DV. 

### What is Deductive Verification?

- Deductive verification is a static verification.
- Each method is checked on its own, using the specifications (not the implementations) of the other methods.
- Each method is checked on its own, using the specifications (not the implementations) of the other methods. 
- Each method is checked on its own, using the specifications (not the implementations) of the other methods.




