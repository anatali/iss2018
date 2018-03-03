package it.unibo.akka.intro;

/*
Actors are objects which encapsulate state and behavior, 
they communicate exclusively by exchanging messages which are placed into the recipient’s mailbox. 
In a sense, actors are the most stringent form of object-oriented programming,
but it serves better to view them as persons.

Like in an economic organization, actors naturally form hierarchies. 
One actor, which is to oversee a certain function in the program might want to split up its task 
into smaller, more manageable pieces. For this purpose it starts child actors which it supervises.
The difficulty in designing such a system is how to decide who should supervise what.

An actor is a container for State, Behavior, a Mailbox, Child Actors and a Supervisor Strategy. 
All of this is encapsulated behind an Actor Reference. 
One noteworthy aspect is that actors have an explicit lifecycle, they are
not automatically destroyed when no longer referenced; after having created one, it is your responsibility to make
sure that it will eventually be terminated as well—which also gives you control over how resources are released
When an Actor Terminates.

Actors are asynchronous by nature: an actor can progress after a
message send without waiting for the actual delivery to happen.
Supervision describes a dependency relationship between actors: 
the supervisor delegates tasks to subordinates and therefore must respond to their failures.
When a subordinate detects a failure (i.e.throws an exception), it suspends itself and 
all its subordinates and sends a message to its supervisor, signaling failure.

Parental supervision: Actors can only be created by other actors—where the top-level actor 
is provided by the library—and each created actor is supervised by its parent. 
This restriction makes the formation of actor supervision hierarchies implicit and encourages sound design decisions. 
It should be noted that this also guarantees that actors cannot be orphaned or attached to supervisors 
from the outside, which might otherwise catch them unawares. 
In addition, this yields a natural and clean shutdown procedure for (sub-trees of) actor applications.
 
An actor system will during its creation start at least three actors:
/user: The Guardian Actor. Actors created using system.actorOf() are children of this actor.
/system: The System Guardian. The system guardian watch the user guardian
/: The Root Guardian. It is the grand-parent of all so-called “top-level” actors. 
						The supervisor of the root guardian cannot be a real actor
Moreover: 
/deadLetters: where all messages sent to stopped or non-existing actors are re-routed 
/remote:  is an artificial path below which all actors reside whose supervisors are remote actor references

Actors are created by passing a Props instance into the actorOf factory method 
which is available on ActorSystem and ActorContext.

Using the ActorSystem will create top-level actors, supervised by the actor system’s provided guardian actor, 
while using an actor’s context will create a child actor.
ActorSystem is a heavy object: create only one per application

The call to actorOf returns an instance of ActorRef. 
This is a handle to the actor instance and the only way to interact with it. 
The ActorRef is immutable and has a one to one relationship with the Actor it represents. 
The ActorRef is also serializable and network-aware.
This means that you can serialize it, send it over the wire and use it on a remote host
and it will still be representing the same Actor on the original node, across the network.

Right after starting the actor, its preStart method is invoked

The UntypedActor class defines only one abstract method, the  
onReceive(Object message), which implements the behavior of the actor.
Each actor path has an address component, describing the protocol and location by which the corresponding actor
is reachable, followed by the names of the actors in the hierarchy from the root up. Examples are:
"akka://my-sys/user/service-a/worker1" // purely local
"akka.tcp://my-sys@host.example.com:5678/user/service-b" // remote

Actor references may be looked up using the ActorSystem.actorSelection method.
The ctorContext.actorSelection method is available inside any actor as context.actorSelection. 
This yields an actor selection much like its twin on ActorSystem, 
but instead of looking up the path starting from the root of the actor tree it starts out on the
current actor. Path elements consisting of two dots ("..") may be used to access the parent actor. You can for
example send a message to a specific sibling:
context.actorSelection("../brother") ! msg
Absolute paths may of course also be looked up on context in the usual way, i.e.
context.actorSelection("/user/serviceA") ! msg
Since the actor system forms a file-system like hierarchy, matching on paths is possible in the same way as supported
by Unix shells

An actor path consists of an anchor, which identifies the actor system, followed by the concatenation of the path
elements, from root guardian to the designated actor; the path elements are the names of the traversed actors and
are separated by slashes.




You can start using Akka without defining any configuration, since sensible default values are provided.
Akka uses the Typesafe Config Library

By default the dispatcher provided by Akka is one with a fork-join-executor, and the default parallelism values are these:
parallelism-min: 8
parallelism-factor: 3.0
parallelism-max: 64

Sometimes it can be useful to include another configuration file, 
for example if you have one application.conf with
all environment independent settings and then override some settings for specific environments.
Specifying system property with -Dconfig.resource=/dev.conf will load the dev.conf file, 
which includes the application.conf

ActorSystem is the only consumer of configuration information. While constructing an actor
system, you can either pass in a Config object or not, where the second case is equivalent to passing
ConfigFactory.load() (with the right class loader)


 akka actors behave like peers rather than client-server. 
 So both local and remote actors should talk on similar transport. 
 So the only difference between remote and local, is which machine they running. 
 If it’s running in remote machine, then it’s a remote actor and if it’s in your machine then it’s local actor.

The configuration is exactly same other than the port. Port 0 means any free port.

Right after starting the actor, its preStart method is invoked.
This method is called when the actor is first created. During restarts it is called by the default implementation of
postRestart, which means that by overriding that method you can choose whether the initialization code in
this method is called only exactly once for this actor or for every restart. Initialization code which is part of the
actor’s constructor will always be called when an instance of the actor class is created, which happens at every
restart.


DISPATCHER
An Akka MessageDispatcher is what makes Akka Actors “tick”, it is the engine of the machine so to speak.
All MessageDispatcher implementations are also an ExecutionContext, which means that they can be
used to execute arbitrary code, for instance Futures.

There are 3 different types of message dispatchers: Dispatcher, PinnedDispatcher, CallingThreadDispatcher

Every ActorSystem will have a default dispatcher that will be used in case nothing else is configured
for an Actor. The default dispatcher can be configured, and is by default a Dispatcher
with the specified default-executor. If an ActorSystem is created with an ExecutionContext
passed in, this ExecutionContext will be used as the default executor for all dispatchers in
this ActorSystem. If no ExecutionContext is given, it will fallback to the executor specified in
akka.actor.default-dispatcher.default-executor.fallback. By default this is a “forkjoin-
executor”, which gives excellent performance in most cases.

Deployment settings for specific actors can be defined in the akka.actor.deployment section of the configuration.
In the deployment section it is possible to define things like dispatcher, mailbox, router settings, and
remote deployment.

FUTURES
A common use case within Akka is to have some computation performed concurrently without needing the extra
utility of an UntypedActor. If you find yourself creating a pool of UntypedActors for the sole reason of
performing a calculation in parallel, there is an easier (and faster) way.

AGENTS
Agents in Akka are inspired by agents in Clojure.
Agents are reactive.

FSM 
The FSM (Finite State Machine) is available as an abstract base class that implements an Akka Actor and is best
described in the Erlang design principles
A FSM can be described as a set of relations of the form:
	State(S) x Event(E) -> Actions (A), State(S’)
These relations are interpreted as meaning:
If we are in state S and the event E occurs, we should perform the actions A and make a transition to
the state S’.

 */
public class AkkaIntro {

}
